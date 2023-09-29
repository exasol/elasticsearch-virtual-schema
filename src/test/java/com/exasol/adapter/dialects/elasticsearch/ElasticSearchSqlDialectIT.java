package com.exasol.adapter.dialects.elasticsearch;

import static com.exasol.adapter.dialects.elasticsearch.ITConfiguration.*;
import static com.exasol.matcher.ResultSetStructureMatcher.table;
import static jakarta.json.Json.createObjectBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.sql.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.util.StringUtils;
import org.opentest4j.AssertionFailedError;
import org.testcontainers.containers.JdbcDatabaseContainer.NoDriverFoundException;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
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
import com.exasol.matcher.TypeMatchMode;
import com.exasol.udfdebugging.UdfTestSetup;

import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Tag("integration")
@Testcontainers
class ElasticSearchSqlDialectIT {
    private static final Logger LOGGER = Logger.getLogger(ElasticSearchSqlDialectIT.class.getName());
    @Container
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>().withReuse(true);
    @Container
    private static final ElasticsearchContainer ES_CONTAINER = createElasticsearchContainer();

    private static final String VIRTUAL_SCHEMA_NAME = "VIRTUAL_SCHEMA_ES";
    private static final String INDEX_NAME = "index";
    private static final String ASSERT_FIELD = "ASSERT_FIELD";
    private static final String ASSERT_VALUE = "ASSERT_VALUE";

    private static final Matcher<ResultSet> EMPTY_TABLE_MATCHER = getEmptyTableMatcher();

    private static ElasticsearchContainer createElasticsearchContainer() {
        final ElasticsearchContainer container = new ElasticsearchContainer(ELASTICSEARCH_DOCKER_IMAGE_REFERENCE);
        // Disable TLS for Elasticsearch server as the self-signed certificate uses the wrong hostname and we can't
        // configure a HostnameVerifier for the JDBC driver.
        container.withEnv("ES_SETTING_XPACK_SECURITY_ENABLED", "false");
        container.withEnv("ES_SETTING_DISCOVERY_TYPE", "single-node");
        container
                .setWaitStrategy(new LogMessageWaitStrategy().withRegEx(".*\"license .* mode \\[basic\\] - valid\".*"));
        return container;
    }

    private static Matcher<ResultSet> getEmptyTableMatcher() {
        return table("VARCHAR").matches(TypeMatchMode.NO_JAVA_TYPE_CHECK);
    }

    private static final Matcher<ResultSet> SINGLE_ROW_TABLE_MATCHER = getSingleRowTableMatcher();

    private static Matcher<ResultSet> getSingleRowTableMatcher() {
        return table().row(ASSERT_VALUE).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK);
    }

    private static Connection connection;
    private static ExasolObjectFactory objectFactory;
    private static ExasolSchema adapterSchema;
    private static AdapterScript adapterScript;
    private static VirtualSchema virtualSchema;
    private static ConnectionDefinition jdbcConnection;
    private static UdfTestSetup udfTestSetup;
    private static ElasticSearchGateway esGateway;

    @BeforeAll
    static void beforeAll()
            throws BucketAccessException, TimeoutException, IOException, NoDriverFoundException, SQLException {
        connection = EXASOL.createConnection();
        objectFactory = setupObjectFactory();
        adapterSchema = objectFactory.createSchema("ADAPTER_SCHEMA");
        adapterScript = installVirtualSchemaAdapter(adapterSchema);
        esGateway = ElasticSearchGateway.connectTo(ES_CONTAINER);
        esGateway.startTrial();
        esGateway.closeConnection();
    }

    private static ExasolObjectFactory setupObjectFactory() {
        udfTestSetup = new UdfTestSetup(getJdbcUrlHost(), EXASOL.getDefaultBucket(), connection);
        return new ExasolObjectFactory(connection,
                ExasolObjectConfiguration.builder().withJvmOptions(udfTestSetup.getJvmOptions()).build());
    }

    private static AdapterScript installVirtualSchemaAdapter(final ExasolSchema adapterSchema)
            throws BucketAccessException, TimeoutException, FileNotFoundException {
        final Bucket bucket = EXASOL.getDefaultBucket();
        bucket.uploadFile(VIRTUAL_SCHEMAS_JAR_PATH, VIRTUAL_SCHEMAS_JAR_NAME_AND_VERSION);
        uploadDriverToBucket(bucket);
        return adapterSchema.createAdapterScriptBuilder("ADAPTER_SCRIPT") //
                .language(Language.JAVA) //
                .content(getAdapterScriptContent()).build();
    }

    private static void uploadDriverToBucket(final Bucket bucket)
            throws TimeoutException, BucketAccessException, FileNotFoundException {
        try {
            bucket.uploadFile(SETTINGS_FILE_PATH, JDBC_DRIVERS_IN_BUCKET_PATH + SETTINGS_FILE_NAME);
            bucket.uploadFile(JDBC_DRIVER_PATH, JDBC_DRIVERS_IN_BUCKET_PATH + JDBC_DRIVER_NAME);
        } catch (final BucketAccessException | FileNotFoundException exception) {
            LOGGER.severe(ExaError.messageBuilder("F-VSES-2")
                    .message("An error occurred while uploading the jdbc driver to the bucket.")
                    .mitigation("Make sure the {{JDBC_DRIVER_PATH}} file exists.")
                    .parameter("JDBC_DRIVER_PATH", JDBC_DRIVER_PATH)
                    .mitigation("You can generate it by executing the integration test with maven.").toString());
            throw exception;
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
        udfTestSetup.close();
        connection.close();
    }

    @BeforeEach
    void beforeEach() throws IOException {
        virtualSchema = null;
        jdbcConnection = createAdapterConnectionDefinition();
        esGateway = ElasticSearchGateway.connectTo(ES_CONTAINER);
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
        return objectFactory.createConnectionDefinition("JDBC_CONNECTION", getJdbcUrl());
    }

    private String getJdbcUrl() {
        final Integer port = ES_CONTAINER.getMappedPort(9200);
        final String host = getJdbcUrlHost();
        if (ES_CONTAINER.caCertAsBytes().isPresent()) {
            final String trustStorePassword = "trustStorePassword";
            final String truststorePath = uploadCaTruststore(trustStorePassword);
            return "jdbc:es://https://" + host + ":" + port + "?ssl=true&ssl.truststore.location=" + truststorePath
                    + "&ssl.truststore.pass=" + trustStorePassword + "&ssl.truststore.type=JKS";
        } else {
            return "jdbc:es://http://" + host + ":" + port;
        }
    }

    private static String getJdbcUrlHost() {
        return ES_CONTAINER.getHost().equals("localhost") ? ITConfiguration.DOCKER_IP_ADDRESS : ES_CONTAINER.getHost();
    }

    private String uploadCaTruststore(final String trustStorePassword) {
        final Path truststorePath = generateCaTruststore(ES_CONTAINER.caCertAsBytes().get(), getJdbcUrlHost(),
                trustStorePassword);
        try {
            EXASOL.getDefaultBucket().uploadFile(truststorePath, truststorePath.getFileName().toString());
        } catch (FileNotFoundException | BucketAccessException | TimeoutException exception) {
            throw new IllegalStateException("Failed to upload file to BucketFS", exception);
        }
        return DEFAULT_BUCKET_PATH + truststorePath.getFileName().toString();
    }

    private Path generateCaTruststore(final byte[] caCert, final String certificateHost,
            final String trustStorePassword) {
        try {
            final Path tempFile = Files.createTempFile("certTruststore", "jks");
            final KeyStore keyStore = KeyStore.getInstance("JKS");
            final char[] pwdArray = trustStorePassword.toCharArray();
            keyStore.load(null, pwdArray);
            final Certificate certificate = CertificateFactory.getInstance("X.509")
                    .generateCertificate(new ByteArrayInputStream(caCert));
            keyStore.setCertificateEntry(certificateHost, certificate);
            try (OutputStream fos = Files.newOutputStream(tempFile)) {
                keyStore.store(fos, pwdArray);
            }
            return tempFile;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException exception) {
            throw new IllegalStateException("Failed to generate truststore", exception);
        }
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
        assertVirtualTableContentsByQuery(query, table().row(Boolean.TRUE).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
    }

    @Test
    void testDocumentWithIntegerProperty() throws IOException {
        this.indexDocument(createObjectBuilder().add("int_field", 1).build());
        final String query = "SELECT \"int_field\"" //
                + " FROM " + getVirtualTableName() //
                + " WHERE \"int_field\" = 1";
        assertVirtualTableContentsByQuery(query, table().row(1).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
    }

    @Test
    void testDocumentWithStringProperty() throws IOException {
        this.indexDocument(createObjectBuilder().add("str_field", "str").build());
        final String query = "SELECT \"str_field\"" //
                + " FROM " + getVirtualTableName() //
                + " WHERE \"str_field\" = 'str'";
        assertVirtualTableContentsByQuery(query, table().row("str").matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
    }

    @Test
    void testDocumentWithNestedProperty() throws IOException {
        final JsonObject innerField = createObjectBuilder().add("inner_str_field", "inner_str").build();
        this.indexDocument(createObjectBuilder().add("str_field", "str").add("inner_field", innerField).build());
        final String query = "SELECT  \"str_field\", \"inner_field/inner_str_field\""//
                + " FROM " + getVirtualTableName();
        assertVirtualTableContentsByQuery(query,
                table().row("str", "inner_str").matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
    }

    @Test
    void testSelectAllColumns() throws IOException {
        this.indexDocument(createObjectBuilder().add("c1", "str").add("c2", 42).add("c3", 3.14).build());
        final String query = "SELECT * FROM " + getVirtualTableName();
        assertVirtualTableContentsByQuery(query,
                table().row("str", "str", 42, 3.140000104904175).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
    }

    @Nested
    @DisplayName("Main Capabilities test")
    class MainCapabilitiesTest {
        @Test
        void testSelectListProjection() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").build());
            final String query = "SELECT \"str_field\" FROM " + getVirtualTableName();
            assertVirtualTableContentsByQuery(query, table().row("str").matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testSelectListWithExpressions() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 1).build());
            final String query = "SELECT \"int_field\"+1 FROM " + getVirtualTableName();
            assertVirtualTableContentsByQuery(query, table().row(2).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testFilterExpressions() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 2).build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " WHERE \"int_field\" <= 1";
            assertVirtualTableContentsByQuery(query, table().row(1).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testAggregateSingleGroup() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 2).build());
            final String query = "SELECT min(\"int_field\") FROM " + getVirtualTableName();
            assertVirtualTableContentsByQuery(query, table().row(1).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testAggregateGroupByColumn() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            final String query = "SELECT \"str_field\", min(\"int_field\")" //
                    + " FROM " + getVirtualTableName() //
                    + " GROUP BY \"str_field\"";
            assertVirtualTableContentsByQuery(query, table().row("str", 1).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testAggregateGroupByTuple() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            final String query = "SELECT \"str_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " GROUP BY \"str_field\", \"int_field\"";
            assertVirtualTableContentsByQuery(query, table().row("str").matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testAggregateHaving() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            final String query = "SELECT \"str_field\", min(\"int_field\")" //
                    + " FROM " + getVirtualTableName() //
                    + " GROUP BY \"str_field\"" //
                    + " HAVING min(\"int_field\") = 1";
            assertVirtualTableContentsByQuery(query, table().row("str", 1).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testOrderByColumnASC() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 2).build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\" ASC";
            assertVirtualTableContentsByQuery(query, table().row(1).row(2).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testOrderByColumnDESC() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 2).build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\" DESC";
            assertVirtualTableContentsByQuery(query, table().row(2).row(1).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testOrderByMultipleColumnASC() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "a").add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 2).build());
            indexDocument(createObjectBuilder().add("str_field", "str").add("int_field", 3).build());
            final String query = "SELECT \"str_field\", \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"str_field\", \"int_field\"";
            assertVirtualTableContentsByQuery(query,
                    table().row("a", 1).row("str", 2).row("str", 3).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testOrderByColumnNullsLastASC() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().addNull("int_field").build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\" ASC NULLS LAST";
            assertVirtualTableContentsByQuery(query,
                    table().row(1).row(IsNull.nullValue()).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testOrderByColumnNullsFirstASC() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().addNull("int_field").build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\" ASC NULLS FIRST";
            assertVirtualTableContentsByQuery(query,
                    table().row(IsNull.nullValue()).row(1).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testOrderByExpression() throws IOException {
            indexDocument(createObjectBuilder().add("int_field", 1).build());
            indexDocument(createObjectBuilder().add("int_field", 3).build());
            final String query = "SELECT \"int_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " ORDER BY \"int_field\"+1 DESC";
            assertVirtualTableContentsByQuery(query, table().row(3).row(1).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        @Test
        void testLimit() throws IOException {
            indexDocument(createObjectBuilder().add("str_field", "str").build());
            indexDocument(createObjectBuilder().add("str_field", "str").build());
            final String query = "SELECT \"str_field\"" //
                    + " FROM " + getVirtualTableName() //
                    + " LIMIT 1";
            assertVirtualTableContentsByQuery(query, table().row("str").matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
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
                    table().row(ASSERT_VALUE).row(ASSERT_VALUE).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
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

            private AggregateFunctionVerifier withValues(final int... values) {
                this.values = values;
                return this;
            }

            private AggregateFunctionVerifier applyToStar() {
                this.useStar = true;
                return this;
            }

            private AggregateFunctionVerifier distinct() {
                this.distinct = "DISTINCT ";
                return this;
            }

            private AggregateFunctionVerifier withResult(final Object result) {
                this.result = result;
                return this;
            }

            private void verify() throws IOException {
                for (final int value : this.values) {
                    indexDocumentWithGenericTestField(createObjectBuilder().add(NUMERIC_TEST_FIELD, value));
                }
                assertVirtualTableContentsByQuery(this.getQuery(),
                        table().row(ASSERT_VALUE, this.result).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
            }

            private String getQuery() {
                return "SELECT \"" + ASSERT_FIELD + "\", " + this.aggregateFunction + "(" + this.distinct
                        + this.getFunctionArgument() + ")" //
                        + " FROM " + getVirtualTableName() //
                        + " GROUP BY \"" + ASSERT_FIELD + "\"";
            }

            private String getFunctionArgument() {
                return this.useStar ? "*" : "\"" + NUMERIC_TEST_FIELD + "\"";
            }
        }
    }

    @Nested
    @DisplayName("Scalar Function Capabilities test")
    class ScalarFunctionCapabilitiesTest {
        @Test
        void testAdd() throws IOException {
            assertScalarFunction("+").asBinaryOperator().withValues(1, 1).withResult(2).verify();
        }

        @Test
        void testSub() throws IOException {
            assertScalarFunction("-").asBinaryOperator().withValues(1, 1).withResult(0).verify();
        }

        @Test
        void testMult() throws IOException {
            assertScalarFunction("*").asBinaryOperator().withValues(1, 2).withResult(2).verify();
        }

        @Test
        void testNeg() throws IOException {
            assertScalarFunction("*").asBinaryOperator().withValues(1, -2).withResult(-2).verify();
        }

        @Test
        void testAbs() throws IOException {
            assertScalarFunction("ABS").withValues(-1).withResult(1).verify();
        }

        @Test
        void testACos() throws IOException {
            assertScalarFunction("ACOS").withValues(0.5).withResult(1.0471975511965979).verify();
        }

        @Test
        void testASin() throws IOException {
            assertScalarFunction("ASIN").withValues(1).withResult(1.5707963267948966).verify();
        }

        @Test
        void testATan() throws IOException {
            assertScalarFunction("ATAN").withValues(1).withResult(0.7853981633974483).verify();
        }

        @Test
        void testATan2() throws IOException {
            assertScalarFunction("ATAN2").withValues(1, 1).withResult(0.7853981633974483).verify();
        }

        @Test
        @Disabled("https://github.com/exasol/elasticsearch-virtual-schema/issues/66")
        void testCeil() throws IOException {
            assertScalarFunction("CEIL").withValues(0.234).withResult(1).verify();
        }

        @Test
        void testCeilWithBug() throws IOException {
            // See https://github.com/exasol/elasticsearch-virtual-schema/issues/66
            assertNumberConversionFails(() -> assertScalarFunction("CEIL").withValues(0.234).withResult(1).verify());
        }

        private void assertNumberConversionFails(final Executable executable) {
            final AssertionError error = assertThrows(AssertionError.class, executable);
            assertThat(error.getMessage(), containsString("ETL-1299: Failed to create transformator"));
        }

        @Test
        void testCos() throws IOException {
            assertScalarFunction("COS").withValues(0.5).withResult(0.8775825618903728).verify();
        }

        @Test
        void testCosh() throws IOException {
            assertScalarFunction("COSH").withValues(1).withResult(1.543080634815244).verify();
        }

        @Test
        void testCot() throws IOException {
            assertScalarFunction("COT").withValues(1).withResult(0.6420926159343306).verify();
        }

        @Test
        void testDegrees() throws IOException {
            assertScalarFunction("DEGREES").withValues(0.5).withResult(28.64788975654116).verify();
        }

        @Test
        void testDiv() throws IOException {
            assertScalarFunction("DIV").withValues(15, 6).withResult(2).verify();
        }

        @Test
        void testExp() throws IOException {
            assertScalarFunction("EXP").withValues(1).withResult(2.718281828459045).verify();
        }

        @Test
        @Disabled("https://github.com/exasol/elasticsearch-virtual-schema/issues/66")
        void testFloor() throws IOException {
            assertScalarFunction("FLOOR").withValues(4.567).withResult(4).verify();
        }

        @Test
        void testFloorWithBug() throws IOException {
            // See https://github.com/exasol/elasticsearch-virtual-schema/issues/66
            assertNumberConversionFails(() -> assertScalarFunction("FLOOR").withValues(4.567).withResult(4).verify());
        }

        @Test
        void testGreatest() throws IOException {
            assertScalarFunction("GREATEST").withValues(1, 5, 3).withResult(5).verify();
        }

        @Test
        void testLeast() throws IOException {
            assertScalarFunction("LEAST").withValues(1, 5, 3).withResult(1).verify();
        }

        @Test
        void testLN() throws IOException {
            assertScalarFunction("LN").withValues(100).withResult(4.605170185988092).verify();
        }

        @Test
        void testMod() throws IOException {
            assertScalarFunction("MOD").withValues(15, 6).withResult(3).verify();
        }

        @Test
        void testPower() throws IOException {
            assertScalarFunction("POWER").withValues(2, 10).withResult(1024.0).verify();
        }

        @Test
        void testRadians() throws IOException {
            assertScalarFunction("RADIANS").withValues(180).withResult(3.141592653589793).verify();
        }

        @Test
        void testRound() throws IOException {
            assertScalarFunction("ROUND").withValues(123.456, 2).withResult(123.45999908447266).verify();
        }

        @Test
        void testSign() throws IOException {
            assertScalarFunction("SIGN").withValues(-123).withResult(-1).verify();
        }

        @Test
        void testSin() throws IOException {
            assertScalarFunction("SIN").withValues(1).withResult(0.8414709848078965).verify();
        }

        @Test
        void testSinh() throws IOException {
            assertScalarFunction("SINH").withValues(0).withResult(0.0).verify();
        }

        @Test
        void testSqrt() throws IOException {
            assertScalarFunction("SQRT").withValues(2).withResult(1.4142135623730951).verify();
        }

        @Test
        void testTan() throws IOException {
            assertScalarFunction("TAN").withValues(4).withResult(1.1578212823495775).verify();
        }

        @Test
        @Disabled("Bug in Exasol")
        void testTrunc() throws IOException {
            assertScalarFunction("TRUNC").withValues(123.456, 2).withResult(123.45).verify();
        }

        @Test
        void testTruncWithBug() throws IOException {
            assertScalarFunction("TRUNC").withValues(123.456, 2).withResult(123.44999694824219).verify();
        }

        @Test
        void testAscii() throws IOException {
            assertScalarFunction("ASCII").withValues("X").withResult(88).verify();
        }

        @Test
        void testBitLength() throws IOException {
            assertScalarFunction("BIT_LENGTH").withValues("aou").withResult(24).verify();
        }

        @Test
        void testBitLengthSpecialChars() throws IOException {
            assertScalarFunction("BIT_LENGTH").withValues("äöü").withResult(48).verify();
        }

        @ParameterizedTest
        @CsvSource({ "CHAR", "CHR" })
        void testChar(final String charScalarFunctionAlias) throws IOException {
            assertScalarFunction(charScalarFunctionAlias).withValues(88).withResult("X").verify();
        }

        @Test
        void testConcat() throws IOException {
            assertScalarFunction("CONCAT").withValues("abc", "def").withResult("abcdef").verify();
        }

        @Test
        void testInsertLongerThanString() throws IOException {
            assertScalarFunction("INSERT").withValues("abc", 2, 2, "xxx").withResult("axxx").verify();
        }

        @Test
        void testInsertShorterThanString() throws IOException {
            assertScalarFunction("INSERT").withValues("abcdef", 3, 2, "CD").withResult("abCDef").verify();
        }

        @Test
        void testLength() throws IOException {
            assertScalarFunction("LENGTH").withValues("abc").withResult(3).verify();
        }

        @Test
        void testOctetLength() throws IOException {
            assertScalarFunction("OCTET_LENGTH").withValues("abcd").withResult(4).verify();
        }

        @Test
        void testOctetLengthSpecialChars() throws IOException {
            assertScalarFunction("OCTET_LENGTH").withValues("äöü").withResult(6).verify();
        }

        @Test
        void testRepeat() throws IOException {
            assertScalarFunction("REPEAT").withValues("abc", 3).withResult("abcabcabc").verify();
        }

        @Test
        void testReplace() throws IOException {
            assertScalarFunction("REPLACE").withValues("Apple juice is great", "Apple", "Orange")
                    .withResult("Orange juice is great").verify();
        }

        @Test
        void testRight() throws IOException {
            assertScalarFunction("RIGHT").withValues("abcdef", 3).withResult("def").verify();
        }

        @Test
        void testSpace() throws IOException {
            assertScalarFunction("SPACE").withValues(5).withResult("     ").verify();
        }

        @Test
        void testDateTruncMonth() throws IOException {
            assertScalarFunction("DATE_TRUNC").withValues("month", "2006-12-31")
                    .withResult(Timestamp.valueOf("2006-12-01 00:00:00.0")).verify();
        }

        @Test
        void testDateTruncMinute() throws IOException {
            assertScalarFunction("DATE_TRUNC").withValues("minute", "2018-02-19T10:23:27Z")
                    .withResult(Timestamp.valueOf("2018-02-19 11:23:00.0")).verify();
        }

        @Test
        void testDay() throws IOException {
            assertScalarFunction("DAY").withValues("2010-10-20").withResult(20).verify();
        }

        @Test
        @Disabled("https://github.com/exasol/elasticsearch-virtual-schema/issues/65")
        void testExtractSecond() throws IOException {
            assertExtract("SECOND").withValues("2018-02-19T10:23:27Z").withResult(27).verify();
        }

        @Test
        void testExtractSecondWithBug() throws IOException {
            // Workaround for bug in DB. See https://github.com/exasol/elasticsearch-virtual-schema/issues/65
            assertExtract("SECOND").withValues("2018-02-19T10:23:27Z").withResult(0.027).verify();
        }

        @Test
        void testExtractMonth() throws IOException {
            assertExtract("MONTH").withValues("2000-10-01").withResult(10).verify();
        }

        @Test
        void testHour() throws IOException {
            assertScalarFunction("HOUR").withValues("2018-02-19T10:23:27Z").withResult(11).verify();
        }

        @Test
        void testMinute() throws IOException {
            assertScalarFunction("MINUTE").withValues("2018-02-19T10:23:27Z").withResult(23).verify();
        }

        @Test
        void testMonth() throws IOException {
            assertScalarFunction("MONTH").withValues("2010-10-20").withResult(10).verify();
        }

        @Test
        void testWeek() throws IOException {
            assertScalarFunction("WEEK").withValues("2012-01-05").withResult(1).verify();
        }

        @Test
        void testYear() throws IOException {
            assertScalarFunction("YEAR").withValues("2010-10-20").withResult(2010).verify();
        }

        @Test
        void testCastToDate() throws IOException {
            assertCastTo("DATE").withValues("2006-01-01").withResult(Date.valueOf("2006-01-01")).verify();
        }

        @Test
        void testCase() throws IOException {
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 1));
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 2));
            indexDocumentWithGenericTestField(createObjectBuilder().add("int_field", 3));
            final String query = "SELECT CASE \"int_field\" " //
                    + "WHEN 1 THEN 'A' " //
                    + "WHEN 2 THEN 'B' "//
                    + "ELSE 'C' "//
                    + "END FROM " + getVirtualTableName();
            assertVirtualTableContentsByQuery(query,
                    table().row("A").row("B").row("C").matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
        }

        ScalarFunctionVerifier assertExtract(final String extractUnit) {
            return new ScalarFunctionVerifier("EXTRACT").extractUnit(extractUnit);
        }

        ScalarFunctionVerifier assertCastTo(final String castType) {
            return new ScalarFunctionVerifier("CAST").withCastType(castType);
        }

        ScalarFunctionVerifier assertScalarFunction(final String scalarFunction) {
            return new ScalarFunctionVerifier(scalarFunction);
        }

        private class ScalarFunctionVerifier {
            private static final String TEST_FIELD = "TEST_FIELD";
            private final String scalarFunction;
            private boolean isBinaryOperator = false;
            private Object[] values = {};
            private Object result;
            private String castType = "";
            private String extractUnit = "";

            private ScalarFunctionVerifier(final String scalarFunction) {
                this.scalarFunction = scalarFunction;
            }

            public ScalarFunctionVerifier extractUnit(final String extractUnit) {
                this.extractUnit = extractUnit;
                return this;
            }

            public ScalarFunctionVerifier withCastType(final String castType) {
                this.castType = castType;
                return this;
            }

            public ScalarFunctionVerifier asBinaryOperator() {
                this.isBinaryOperator = true;
                return this;
            }

            private ScalarFunctionVerifier withValues(final Object... values) {
                this.values = values;
                return this;
            }

            private ScalarFunctionVerifier withResult(final Object result) {
                this.result = result;
                return this;
            }

            private void verify() {
                this.indexDocument();
                assertVirtualTableContentsByQuery(this.getQuery(),
                        table().row(ASSERT_VALUE, this.result).matches(TypeMatchMode.NO_JAVA_TYPE_CHECK));
            }

            private void indexDocument() {
                int fieldNumber = 0;
                final JsonObjectBuilder objectBuilder = createObjectBuilder();
                for (final Object value : this.values) {
                    if (value instanceof String) {
                        objectBuilder.add(TEST_FIELD + fieldNumber, (String) value);
                    }
                    if (value instanceof Integer) {
                        objectBuilder.add(TEST_FIELD + fieldNumber, (Integer) value);
                    }
                    if (value instanceof Double) {
                        objectBuilder.add(TEST_FIELD + fieldNumber, (Double) value);
                    }
                    fieldNumber++;
                }
                indexDocumentWithGenericTestField(objectBuilder);
            }

            private String getQuery() {
                return "SELECT \"" + ASSERT_FIELD + "\", " + this.getScalarFunction() //
                        + " FROM " + getVirtualTableName();
            }

            private String getScalarFunction() {
                final List<String> arguments = this.getFunctionArguments();
                if (arguments.isEmpty()) {
                    return this.scalarFunction;
                } else if (!StringUtils.isBlank(this.castType)) {
                    return this.scalarFunction + "(" + String.join(",", this.getFunctionArguments()) + " AS "
                            + this.castType + ")";
                } else if (!StringUtils.isBlank(this.extractUnit)) {
                    return this.scalarFunction + "(" + this.extractUnit + " FROM "
                            + String.join(",", this.getFunctionArguments()) + ")";
                } else if (this.isBinaryOperator) {
                    return String.join(this.scalarFunction, this.getFunctionArguments());
                } else {
                    return this.scalarFunction + "(" + String.join(",", this.getFunctionArguments()) + ")";
                }
            }

            private List<String> getFunctionArguments() {
                return IntStream.range(0, this.values.length).mapToObj(i -> "\"" + TEST_FIELD + i + "\"")
                        .collect(Collectors.toList());
            }
        }
    }

    private void indexDocumentWithGenericTestField(final JsonObjectBuilder documentBuilder) {
        indexDocument(documentBuilder.add(ASSERT_FIELD, ASSERT_VALUE).build());
    }

    private void indexDocument(final JsonObject document) {
        LOGGER.finest(() -> "Elasticsearch: Indexing document " + document);
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
            throw new AssertionFailedError(
                    "Unable to execute assertion query '" + query + "'. Caused by: " + exception.getMessage(),
                    exception);
        }
    }

    private ResultSet query(final String sql) throws SQLException {
        LOGGER.finest(() -> "Executing query " + sql);
        return connection.createStatement().executeQuery(sql);
    }

    private void createVirtualSchema() {
        ElasticSearchSqlDialectIT.virtualSchema = objectFactory.createVirtualSchemaBuilder(VIRTUAL_SCHEMA_NAME)
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
        return "SELECT \"" + ASSERT_FIELD + "\" FROM " + getVirtualTableName();
    }

    private String getVirtualTableName() {
        return VIRTUAL_SCHEMA_NAME + ".\"" + INDEX_NAME + "\"";
    }
}
