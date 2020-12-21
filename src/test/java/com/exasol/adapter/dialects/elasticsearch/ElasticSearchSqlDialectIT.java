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
    void testSelectListProjection() throws IOException {
        this.indexDocument(createObjectBuilder().add("str_field", "str").build());
        final String query = "SELECT \"str_field\" FROM " + getVirtualTableName();
        assertVirtualTableContentsByQuery(query, table().row("str").matchesFuzzily());
    }

    @Test
    void testSelectListWithExpressions() throws IOException {
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 1).build());
        final String query = "SELECT \"int_field\"+1 FROM " + getVirtualTableName();
        assertVirtualTableContentsByQuery(query, table().row(2).matchesFuzzily());
    }

    @Test
    void testFilterExpressions() throws IOException {
        this.indexDocument(createObjectBuilder().add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().add("int_field", 2).build());
        final String query = "SELECT \"int_field\"" //
                + " FROM " + getVirtualTableName() //
                + " WHERE \"int_field\" = 1";
        assertVirtualTableContentsByQuery(query, table().row(1).matchesFuzzily());
    }

    @Test
    void testAggregateSingleGroup() throws IOException {
        this.indexDocument(createObjectBuilder().add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().add("int_field", 2).build());
        final String query = "SELECT min(\"int_field\") FROM " + getVirtualTableName();
        assertVirtualTableContentsByQuery(query, table().row(1).matchesFuzzily());
    }

    @Test
    void testAggregateGroupByColumn() throws IOException {
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
        final String query = "SELECT \"str_field\", min(\"int_field\")" //
                + " FROM " + getVirtualTableName() //
                + " GROUP BY \"str_field\"";
        assertVirtualTableContentsByQuery(query, table().row("str", 1).matchesFuzzily());
    }

    @Test
    void testAggregateGroupByTuple() throws IOException {
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
        final String query = "SELECT \"str_field\"" //
                + " FROM " + getVirtualTableName() //
                + " GROUP BY \"str_field\", \"int_field\"";
        assertVirtualTableContentsByQuery(query, table().row("str").matchesFuzzily());
    }

    @Test
    void testAggregateHaving() throws IOException {
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
        final String query = "SELECT \"str_field\", min(\"int_field\")" //
                + " FROM " + getVirtualTableName() //
                + " GROUP BY \"str_field\"" //
                + " HAVING min(\"int_field\") = 1";
        assertVirtualTableContentsByQuery(query, table().row("str", 1).matchesFuzzily());
    }

    @Test
    void testOrderByColumnASC() throws IOException {
        this.indexDocument(createObjectBuilder().add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().add("int_field", 2).build());
        final String query = "SELECT \"int_field\"" //
                + " FROM " + getVirtualTableName() //
                + " ORDER BY \"int_field\" ASC";
        assertVirtualTableContentsByQuery(query, table().row(1).row(2).matchesFuzzily());
    }

    @Test
    void testOrderByColumnDESC() throws IOException {
        this.indexDocument(createObjectBuilder().add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().add("int_field", 2).build());
        final String query = "SELECT \"int_field\"" //
                + " FROM " + getVirtualTableName() //
                + " ORDER BY \"int_field\" DESC";
        assertVirtualTableContentsByQuery(query, table().row(2).row(1).matchesFuzzily());
    }

    @Test
    void testOrderByMultipleColumnASC() throws IOException {
        this.indexDocument(createObjectBuilder().add("str_field", "a").add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 3).build());
        final String query = "SELECT \"str_field\", \"int_field\"" //
                + " FROM " + getVirtualTableName() //
                + " ORDER BY \"str_field\", \"int_field\"";
        assertVirtualTableContentsByQuery(query, table().row("a", 1).row("str", 2).row("str", 3).matchesFuzzily());
    }

    @Test
    void testOrderByColumnNullsLastASC() throws IOException {
        this.indexDocument(createObjectBuilder().add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().addNull("int_field").build());
        final String query = "SELECT \"int_field\"" //
                + " FROM " + getVirtualTableName() //
                + " ORDER BY \"int_field\" ASC NULLS LAST";
        assertVirtualTableContentsByQuery(query, table().row(1).row(IsNull.nullValue()).matchesFuzzily());
    }

    @Test
    void testOrderByColumnNullsFirstASC() throws IOException {
        this.indexDocument(createObjectBuilder().add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().addNull("int_field").build());
        final String query = "SELECT \"int_field\"" //
                + " FROM " + getVirtualTableName() //
                + " ORDER BY \"int_field\" ASC NULLS FIRST";
        assertVirtualTableContentsByQuery(query, table().row(IsNull.nullValue()).row(1).matchesFuzzily());
    }

    @Test
    void testOrderByExpression() throws IOException {
        this.indexDocument(createObjectBuilder().add("int_field", 1).build());
        this.indexDocument(createObjectBuilder().add("int_field", 3).build());
        final String query = "SELECT \"int_field\"" //
                + " FROM " + getVirtualTableName() //
                + " ORDER BY \"int_field\"+1 DESC";
        assertVirtualTableContentsByQuery(query, table().row(3).row(1).matchesFuzzily());
    }

    @Test
    void testLimit() throws IOException {
        this.indexDocument(createObjectBuilder().add("str_field", "str").build());
        this.indexDocument(createObjectBuilder().add("str_field", "str").build());
        final String query = "SELECT \"str_field\"" //
                + " FROM " + getVirtualTableName() //
                + " LIMIT 1";
        assertVirtualTableContentsByQuery(query, table().row("str").matchesFuzzily());
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

    private void indexDocument(final JsonObject document) throws IOException {
        esGateway.indexDocument(INDEX_NAME, document.toString());
    }

    private void assertVirtualTableContentsByQuery(final String query, final Matcher<ResultSet> matcher) {
        createVirtualSchema();
        try {
            assertThat(query(query), matcher);
        } catch (final SQLException exception) {
            fail("Unable to execute assertion query. Caused by: " + exception.getMessage());
        }
    }

    private void createVirtualSchema() {
        ElasticSearchSqlDialectIT.virtualSchema = objectFactory.createVirtualSchemaBuilder(VIRTUAL_SCHEMA_NAME)
                .dialectName(ES_DIALECT_NAME) //
                .adapterScript(adapterScript) //
                .connectionDefinition(jdbcConnection) //
                .build();
    }

    private String getVirtualTableName() {
        return VIRTUAL_SCHEMA_NAME + ".\"" + INDEX_NAME + "\"";
    }

    private ResultSet query(final String sql) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }
}
