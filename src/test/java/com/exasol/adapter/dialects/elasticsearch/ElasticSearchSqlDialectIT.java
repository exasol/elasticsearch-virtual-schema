package com.exasol.adapter.dialects.elasticsearch;

import static com.exasol.adapter.dialects.elasticsearch.ITConfiguration.*;
import static com.exasol.matcher.ResultSetStructureMatcher.table;
import static javax.json.Json.createObjectBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.JdbcDatabaseContainer.NoDriverFoundException;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.bucketfs.Bucket;
import com.exasol.bucketfs.BucketAccessException;
import com.exasol.containers.ExasolContainer;
import com.exasol.dbbuilder.dialects.DatabaseObject;
import com.exasol.dbbuilder.dialects.exasol.*;
import com.exasol.dbbuilder.dialects.exasol.AdapterScript.Language;
import com.exasol.errorreporting.ExaError;
import com.exasol.udfdebugging.UdfTestSetup;

@Tag("integration")
@Testcontainers
class ElasticSearchSqlDialectIT {
    private static final Logger LOGGER = Logger.getLogger(ElasticSearchSqlDialectIT.class.getName());
    @Container
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            getExasolDockerImageReference()).withReuse(true);
    @Container
    private static final ElasticsearchContainer ES_CONTAINER = new ElasticsearchContainer(
            ELASTICSEARCH_DOCKER_IMAGE_REFERENCE).withReuse(true);
    private static final String ES_DIALECT_NAME = "ES";
    private static final String VIRTUAL_SCHEMA_NAME = "VIRTUAL_SCHEMA_ES";
    private static final String INDEX_NAME = "index";
    private static final String TEST_FIELD = "TEST_FIELD";
    private static final String TEST_VALUE = "TEST_VALUE";

    private static Matcher<ResultSet> EMPTY_TABLE_MATCHER = getEmptyTableMatcher();

    private static Matcher<ResultSet> getEmptyTableMatcher() {
        return table("VARCHAR").matchesFuzzily();
    }

    private static Matcher<ResultSet> SINGLE_ROW_TABLE_MATCHER = getSingleRowTableMatcher();

    private static Matcher<ResultSet> getSingleRowTableMatcher() {
        return table().row(TEST_VALUE).matchesFuzzily();
    }

    private static Connection connection;
    private static ExasolObjectFactory objectFactory;
    private static ExasolSchema adapterSchema;
    private static AdapterScript adapterScript;
    private static VirtualSchema virtualSchema;
    private static ConnectionDefinition jdbcConnection;
    private static ElasticSearchGateway esGateway;

    @BeforeAll
    static void beforeAll() throws BucketAccessException, InterruptedException, TimeoutException, IOException,
            NoDriverFoundException, SQLException {
        connection = EXASOL.createConnection();
        objectFactory = setupObjectFactory();
        adapterSchema = objectFactory.createSchema("ADAPTER_SCHEMA");
        adapterScript = installVirtualSchemaAdapter(adapterSchema);
        ES_CONTAINER.start();
        esGateway = ElasticSearchGateway.connectTo(ES_CONTAINER.getHttpHostAddress());
        esGateway.startTrial();
        esGateway.closeConnection();
    }

    private static ExasolObjectFactory setupObjectFactory() {
        final UdfTestSetup udfTestSetup = new UdfTestSetup(DOCKER_IP_ADDRESS, EXASOL.getDefaultBucket());
        return new ExasolObjectFactory(connection,
                ExasolObjectConfiguration.builder().withJvmOptions(udfTestSetup.getJvmOptions()).build());
    }

    private static AdapterScript installVirtualSchemaAdapter(final ExasolSchema adapterSchema)
            throws InterruptedException, BucketAccessException, TimeoutException {
        final Bucket bucket = EXASOL.getDefaultBucket();
        bucket.uploadFile(VIRTUAL_SCHEMAS_JAR_PATH, VIRTUAL_SCHEMAS_JAR_NAME_AND_VERSION);
        uploadDriverToBucket(bucket);
        return adapterSchema.createAdapterScriptBuilder("ADAPTER_SCRIPT") //
                .language(Language.JAVA) //
                .content(getAdapterScriptContent()).build();
    }

    private static void uploadDriverToBucket(final Bucket bucket)
            throws InterruptedException, TimeoutException, BucketAccessException {
        try {
            bucket.uploadFile(SETTINGS_FILE_PATH, JDBC_DRIVERS_IN_BUCKET_PATH + SETTINGS_FILE_NAME);
            bucket.uploadFile(JDBC_DRIVER_PATH, JDBC_DRIVERS_IN_BUCKET_PATH + JDBC_DRIVER_NAME);
            bucket.uploadFile(JDBC_DRIVER_PATH, JDBC_DRIVER_NAME);
        } catch (final BucketAccessException e) {
            LOGGER.severe(ExaError.messageBuilder("S-ESVS-IT-1")
                    .message("An error occured while uploading the jdbc driver to the bucket.")
                    .mitigation("Make sure the {{JDBC_DRIVER_PATH}} file exists.")
                    .parameter("JDBC_DRIVER_PATH", JDBC_DRIVER_PATH)
                    .mitigation("You can generate it by executing the integration test with maven.").toString());
            throw e;
        }
    }

    private static String getAdapterScriptContent() {
        return "%scriptclass com.exasol.adapter.RequestDispatcher;\n" //
                + "%jar " + DEFAULT_BUCKET_PATH + VIRTUAL_SCHEMAS_JAR_NAME_AND_VERSION + ";\n" //
                + "%jar " + DEFAULT_BUCKET_PATH + JDBC_DRIVERS_IN_BUCKET_PATH + JDBC_DRIVER_NAME + ";\n";
    }

    @AfterAll
    static void afterAll() throws SQLException {
        dropAll(adapterScript, adapterSchema);
        adapterScript = null;
        adapterSchema = null;
        connection.close();
    }

    @BeforeEach
    void beforeEach() throws IOException {
        virtualSchema = null;
        jdbcConnection = createAdapterConnectionDefinition();
        esGateway = ElasticSearchGateway.connectTo(ES_CONTAINER.getHttpHostAddress());
        esGateway.createIndex(INDEX_NAME);
    }

    @AfterEach
    void afterEach() throws IOException {
        dropAll(virtualSchema, jdbcConnection);
        virtualSchema = null;
        jdbcConnection = null;
        esGateway.dropIndex(INDEX_NAME);
        esGateway.closeConnection();
    }

    private ConnectionDefinition createAdapterConnectionDefinition() {
        final String jdbcUrl = "jdbc:es://" + DOCKER_IP_ADDRESS + ":" + ES_CONTAINER.getMappedPort(9200);
        return objectFactory.createConnectionDefinition("JDBC_CONNECTION", jdbcUrl);
    }

    private static void dropAll(final DatabaseObject... databaseObjects) {
        for (final DatabaseObject databaseObject : databaseObjects) {
            if (databaseObject != null) {
                databaseObject.drop();
            }
        }
    }

    @Test
    void testDocumentWithBooleanProperty() throws IOException {
        this.indexDocument(createObjectBuilder().add("bool_field", Boolean.TRUE).build());
        final String query = "SELECT \"bool_field\"" //
                + " FROM " + getVirtualTableName() //
                + " WHERE \"bool_field\" = true";
        assertVirtualTableContentsByQuery(query, table().row(Boolean.TRUE).matchesFuzzily());
    }

    @Test
    void testDocumentWithIntegerProperty() throws IOException {
        this.indexDocument(createObjectBuilder().add("int_field", 1).build());
        final String query = "SELECT \"int_field\"" //
                + " FROM " + getVirtualTableName() //
                + " WHERE \"int_field\" = 1";
        assertVirtualTableContentsByQuery(query, table().row(1).matchesFuzzily());
    }

    @Test
    void testDocumentWithStringProperty() throws IOException {
        this.indexDocument(createObjectBuilder().add("str_field", "str").build());
        final String query = "SELECT \"str_field\"" //
                + " FROM " + getVirtualTableName() //
                + " WHERE \"str_field\" = 'str'";
        assertVirtualTableContentsByQuery(query, table().row("str").matchesFuzzily());
    }

    @Test
    void testDocumentWithNestedProperty() throws IOException {
        final JsonObject innerField = createObjectBuilder().add("inner_str_field", "inner_str").build();
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("inner_field", innerField).build());
        final String query = "SELECT  \"str_field\", \"inner_field/inner_str_field\""//
                + " FROM " + getVirtualTableName();
        assertVirtualTableContentsByQuery(query, table().row("str", "inner_str").matchesFuzzily());
    }

    @Nested
    @DisplayName("Main Capabilities test")
    class MainCapabilitiesTest {
        @Test
        void testSelectListProjection() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").build());
            final String query = "SELECT \"str_field\" FROM " + getVirtualTableName();
            assertVirtualTableContentsByQuery(query, table().row("str").matchesFuzzily());
        }

        @Test
        void testSelectListWithExpressions() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 1).build());
            final String query = "SELECT \"int_field\"+1 FROM " + getVirtualTableName();
            assertVirtualTableContentsByQuery(query, table().row(2).matchesFuzzily());
        }

        @Test
        void testFilterExpressions() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 2).build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " WHERE \"int_field\" <= 1";
            assertVirtualTableContentsByQuery(query, table().row(1).matchesFuzzily());
        }

        @Test
        void testAggregateSingleGroup() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 2).build());
            final String query = "SELECT min(\"int_field\") FROM " + getVirtualTableName();
            assertVirtualTableContentsByQuery(query, table().row(1).matchesFuzzily());
        }

        @Test
        void testAggregateGroupByColumn() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            final String query = "SELECT \"str_field\", min(\"int_field\")" //
                    + " FROM " + getVirtualTableName() //
                    + " GROUP BY \"str_field\"";
            assertVirtualTableContentsByQuery(query, table().row("str", 1).matchesFuzzily());
        }

        @Test
        void testAggregateGroupByTuple() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            final String query = "SELECT \"str_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " GROUP BY \"str_field\", \"int_field\"";
            assertVirtualTableContentsByQuery(query, table().row("str").matchesFuzzily());
        }

        @Test
        void testAggregateHaving() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            final String query = "SELECT \"str_field\", min(\"int_field\")" //
                    + " FROM " + getVirtualTableName() //
                    + " GROUP BY \"str_field\"" //
                    + " HAVING min(\"int_field\") = 1";
            assertVirtualTableContentsByQuery(query, table().row("str", 1).matchesFuzzily());
        }

        @Test
        void testOrderByColumnASC() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 2).build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\" ASC";
            assertVirtualTableContentsByQuery(query, table().row(1).row(2).matchesFuzzily());
        }

        @Test
        void testOrderByColumnDESC() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 2).build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\" DESC";
            assertVirtualTableContentsByQuery(query, table().row(2).row(1).matchesFuzzily());
        }

        @Test
        void testOrderByMultipleColumnASC() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "a").add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 3).build());
            final String query = "SELECT \"str_field\", \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"str_field\", \"int_field\"";
            assertVirtualTableContentsByQuery(query, table().row("a", 1).row("str", 2).row("str", 3).matchesFuzzily());
        }

        @Test
        void testOrderByColumnNullsLastASC() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().addNull("int_field").build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\" ASC NULLS LAST";
            assertVirtualTableContentsByQuery(query, table().row(1).row(IsNull.nullValue()).matchesFuzzily());
        }

        @Test
        void testOrderByColumnNullsFirstASC() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().addNull("int_field").build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\" ASC NULLS FIRST";
            assertVirtualTableContentsByQuery(query, table().row(IsNull.nullValue()).row(1).matchesFuzzily());
        }

        @Test
        void testOrderByExpression() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 3).build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\"+1 DESC";
            assertVirtualTableContentsByQuery(query, table().row(3).row(1).matchesFuzzily());
        }

        @Test
        void testLimit() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").build());
            indexDocument(createObjectBuilder().add("str_field", "str").build());
            final String query = "SELECT \"str_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " LIMIT 1";
            assertVirtualTableContentsByQuery(query, table().row("str").matchesFuzzily());
        }
    }

    @Nested
    @DisplayName("Predicate Capabilities test")
    class PredicateCapabilitiesTest {
        @Test
        void testAndPredicate() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertSingleRowResults("\"int_field\" = 1 AND \"int_field\" = 1");
            assertEmptyResults("\"int_field\" = 1 AND \"int_field\" != 1");
            assertEmptyResults("\"int_field\" != 1 AND \"int_field\" != 1");
        }

        @Test
        void testOrPredicate() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertSingleRowResults("\"int_field\" = 1 OR \"int_field\" = 1");
            assertSingleRowResults("\"int_field\" = 1 OR \"int_field\" != 1");
            assertEmptyResults("\"int_field\" != 1 OR \"int_field\" != 1");
        }

        @Test
        void testNotPredicate() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertEmptyResults("NOT \"int_field\" = 1");
            assertSingleRowResults("NOT \"int_field\" != 1");
        }

        @Test
        void testEqualPredicate() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertEmptyResults("\"int_field\" = 2");
            assertSingleRowResults("\"int_field\" = 1");
        }

        @Test
        void testNotEqualPredicate() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertSingleRowResults("\"int_field\" != 2");
            assertEmptyResults("\"int_field\" != 1");
        }

        @Test
        void testLessPredicate() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertSingleRowResults("\"int_field\" < 2");
            assertEmptyResults("\"int_field\" < 1");
        }

        @Test
        void testLessEqualPredicate() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertSingleRowResults("\"int_field\" <= 1");
            assertEmptyResults("\"int_field\" <= 0");
        }

        @Test
        void testBetweenPredicate() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertSingleRowResults("\"int_field\" BETWEEN 0 AND 1");
            assertEmptyResults("\"int_field\" BETWEEN 2 AND 3");
        }

        @Test
        void testInConstListPredicate() throws IOException, SQLException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertSingleRowResults("\"int_field\" IN (1,2)");
            assertEmptyResults("\"int_field\" IN (3,4)");
        }

        @Test
        void testIsNullPredicate() throws IOException {
            indexDocumentWithGenericTestField(
                    createObjectBuilder().add("not_nullable_str_field", "str").add("nullable_int_field", 1));
            indexDocumentWithGenericTestField(createObjectBuilder().add("not_nullable_str_field", "str"));
            createVirtualSchema();
            assertSingleRowResults("\"nullable_int_field\" IS NULL");
            assertEmptyResults("\"not_nullable_str_field\" IS NULL");
        }

        @Test
        void testIsNotNullPredicate() throws IOException {
            indexDocumentWithGenericTestField(
                    createObjectBuilder().add("not_nullable_str_field", "str").add("nullable_int_field", 1));
            indexDocumentWithGenericTestField(createObjectBuilder().add("not_nullable_str_field", "str"));
            createVirtualSchema();
            assertSingleRowResults("\"nullable_int_field\" IS NOT NULL");
            assertQuery(getSelectTestFieldQuery() + " WHERE \"not_nullable_str_field\" IS NOT NULL",
                    table().row(TEST_VALUE).row(TEST_VALUE).matchesFuzzily());
        }

        @Test
        void testLikePredicate() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("str_field", "abcd"));
            createVirtualSchema();
            assertEmptyResults("\"str_field\" LIKE 'a_d'");
            assertEmptyResults("\"str_field\" LIKE '\\%%d'");
            assertSingleRowResults("\"str_field\" LIKE '%%%cd'");
            assertEmptyResults("\"str_field\" LIKE 'xyz'");
        }
    }

    @Nested
    @DisplayName("Literal Capabilities test")
    class LiteralCapabilitiesTest {
        @Test
        void testBoolLiteral() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("bool_field", Boolean.TRUE));
            createVirtualSchema();
            assertSingleRowResults("\"bool_field\" = true");
            assertEmptyResults("\"bool_field\" =  false");
        }

        @Test
        void testDoubleLiteral() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("double_field", 100.23));
            createVirtualSchema();
            assertSingleRowResults("\"double_field\" = 100.23");
            assertEmptyResults("\"double_field\" =  0.01");
            assertEmptyResults("\"double_field\" = 0.0");
            assertEmptyResults("\"double_field\" = -0.13");
        }

        @Test
        void testExactNumericLiteral() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            createVirtualSchema();
            assertEmptyResults("\"int_field\" = 5");
            assertEmptyResults("\"int_field\" = -1");
            assertSingleRowResults("\"int_field\" = 1");
        }

        @Test
        void testStringLiteral() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("str_field", "str"));
            createVirtualSchema();
            assertEmptyResults("\"str_field\" = 'abc'");
            assertEmptyResults("\"str_field\" = ''");
            assertEmptyResults("\"str_field\" = ' '");
            assertSingleRowResults("\"str_field\" = 'str'");
        }
    }

    @Nested
    @DisplayName("Aggregate Function Capabilities test")
    class AggregateFunctionCapabilitiesTest {
        @Test
        void testCount() throws IOException {
            assertAggregateFunction("COUNT").withValues(1, 1).withResult(2).verify();
        }

        @Test
        void testCountStar() throws IOException {
            assertAggregateFunction("COUNT").withValues(1, 1).applyToStar().withResult(2).verify();
        }

        @Test
        void testCountDistinct() throws IOException {
            assertAggregateFunction("COUNT").distinct().withValues(1, 2).withResult(2).verify();
        }

        @Test
        void testSum() throws IOException {
            assertAggregateFunction("SUM").withValues(1, 2).withResult(3).verify();
        }

        @Test
        void testMin() throws IOException {
            assertAggregateFunction("MIN").withValues(1, 2).withResult(1).verify();
        }

        @Test
        void testMax() throws IOException {
            assertAggregateFunction("MAX").withValues(1, 2).withResult(2).verify();
        }

        @Test
        void testAvg() throws IOException {
            assertAggregateFunction("AVG").withValues(1, 2).withResult(1.5).verify();
        }

        @Test
        void testFirstValue() throws IOException {
            assertAggregateFunction("FIRST_VALUE").withValues(1, 2).withResult(1).verify();
        }

        @Test
        void testLastValue() throws IOException {
            assertAggregateFunction("LAST_VALUE").withValues(1, 2).withResult(2).verify();
        }

        @Test
        void testStdDevPop() throws IOException {
            assertAggregateFunction("STDDEV_POP").withValues(1, 2).withResult(0.5).verify();
        }

        @Test
        void testStdDevSamp() throws IOException {
            assertAggregateFunction("STDDEV_SAMP").withValues(1, 2).withResult(0.7071067811865476).verify();
        }

        @Test
        void testVarPop() throws IOException {
            assertAggregateFunction("VAR_POP").withValues(1, 2).withResult(0.25).verify();
        }

        @Test
        void testVarSamp() throws IOException {
            assertAggregateFunction("VAR_SAMP").withValues(1, 2).withResult(0.5).verify();
        }

        AggregateFunctionVerifier assertAggregateFunction(final String aggregateFunction) {
            return new AggregateFunctionVerifier(aggregateFunction);
        }

        private class AggregateFunctionVerifier {
            private static final String NUMERIC_TEST_FIELD = "NUMERIC_TEST_FIELD";
            private final String aggregateFunction;
            private int[] values;
            private Object result;
            private boolean useStar = false;
            private String distinct = "";

            private AggregateFunctionVerifier(final String aggregateFunction) {
                this.aggregateFunction = aggregateFunction;
            }

            public AggregateFunctionVerifier withValues(final int... values) {
                this.values = values;
                return this;
            }

            public AggregateFunctionVerifier applyToStar() {
                this.useStar = true;
                return this;
            }

            public AggregateFunctionVerifier distinct() {
                this.distinct = "DISTINCT ";
                return this;
            }

            public AggregateFunctionVerifier withResult(final Object result) {
                this.result = result;
                return this;
            }

            private void verify() throws IOException {
                for (final int value : this.values) {
                    indexDocumentWithGenericTestField(createObjectBuilder().add(NUMERIC_TEST_FIELD, value));
                }
                assertVirtualTableContentsByQuery(this.getQuery(),
                        table().row(TEST_VALUE, this.result).matchesFuzzily());
            }

            private String getQuery() {
                return "SELECT \"" + TEST_FIELD + "\", " + this.aggregateFunction + "(" + this.distinct
                        + this.getFunctionArgument() + ")" //
                        + " FROM " + getVirtualTableName() //
                        + " GROUP BY \"" + TEST_FIELD + "\"";
            }

            private String getFunctionArgument() {
                return this.useStar ? "*" : "\"" + NUMERIC_TEST_FIELD + "\"";
            }
        }
    }

    private void indexDocumentWithGenericTestField(final JsonObjectBuilder documentBuilder) throws IOException {
        indexDocument(documentBuilder.add(TEST_FIELD, TEST_VALUE).build());
    }

    private void indexDocument(final JsonObject document) throws IOException {
        esGateway.indexDocument(INDEX_NAME, document.toString());
    }

    private void assertVirtualTableContentsByQuery(final String query, final Matcher<ResultSet> matcher) {
        createVirtualSchema();
        assertQuery(query, matcher);
    }

    private void assertQuery(final String query, final Matcher<ResultSet> matcher) {
        try {
            assertThat(query(query), matcher);
        } catch (final SQLException exception) {
            fail("Unable to execute assertion query. Caused by: " + exception.getMessage());
        }
    }

    private ResultSet query(final String sql) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    private void createVirtualSchema() {
        ElasticSearchSqlDialectIT.virtualSchema = objectFactory.createVirtualSchemaBuilder(VIRTUAL_SCHEMA_NAME)
                .dialectName(ES_DIALECT_NAME) //
                .adapterScript(adapterScript) //
                .connectionDefinition(jdbcConnection) //
                .build();
    }

    private void assertSingleRowResults(final String conditions) {
        assertGenericTestFieldQueryWithWhereClause(conditions, SINGLE_ROW_TABLE_MATCHER);
    }

    private void assertEmptyResults(final String conditions) {
        assertGenericTestFieldQueryWithWhereClause(conditions, EMPTY_TABLE_MATCHER);
    }

    private void assertGenericTestFieldQueryWithWhereClause(final String conditions, final Matcher<ResultSet> matcher) {
        assertGenericTestFieldQuery("WHERE " + conditions, matcher);
    }

    private void assertGenericTestFieldQuery(final String extraQuery, final Matcher<ResultSet> matcher) {
        assertQuery(getSelectTestFieldQuery() + " " + extraQuery, matcher);
    }

    private String getSelectTestFieldQuery() {
        return "SELECT \"" + TEST_FIELD + "\" FROM " + getVirtualTableName();
    }

    private String getVirtualTableName() {
        return VIRTUAL_SCHEMA_NAME + ".\"" + INDEX_NAME + "\"";
    }
}
