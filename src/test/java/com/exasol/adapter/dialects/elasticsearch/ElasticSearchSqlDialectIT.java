package com.exasol.adapter.dialects.elasticsearch;

import static com.exasol.adapter.dialects.elasticsearch.ITConfiguration.*;
import static com.exasol.matcher.ResultSetStructureMatcher.table;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.hamcrest.Matcher;
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

@Tag("integration")
@Testcontainers
public class ElasticSearchSqlDialectIT {
    @Container
    private static final ExasolContainer<? extends ExasolContainer<?>> CONTAINER = new ExasolContainer<>(
            ITConfiguration.getExasolDockerImageReference()).withReuse(true);
    @Container
    private static final ElasticsearchContainer ES_CONTAINER = new ElasticsearchContainer(
            ITConfiguration.getElasticSearchDockerImageReference()).withReuse(true);

    private static final String ES_DIALECT_NAME = "ES";
    private static Connection connection;
    private static ExasolObjectFactory objectFactory;
    private static ExasolSchema adapterSchema;
    private static AdapterScript adapterScript;
    private VirtualSchema virtualSchema;
    private ConnectionDefinition jdbcConnection;
    private static ElasticSearchGateway esGateway;

    @BeforeAll
    static void beforeAll() throws BucketAccessException, InterruptedException, TimeoutException, IOException,
            NoDriverFoundException, SQLException {
        connection = CONTAINER.createConnection("");
        objectFactory = new ExasolObjectFactory(connection);
        adapterSchema = objectFactory.createSchema("ADAPTER_SCHEMA");
        adapterScript = installVirtualSchemaAdapter(adapterSchema);
        ES_CONTAINER.start();
        esGateway = new ElasticSearchGateway(ES_CONTAINER.getHttpHostAddress());
        esGateway.startTrial();
        esGateway.closeConnection();
    }

    private static AdapterScript installVirtualSchemaAdapter(final ExasolSchema adapterSchema)
            throws InterruptedException, BucketAccessException, TimeoutException {
        final Bucket bucket = CONTAINER.getDefaultBucket();
        bucket.uploadFile(PATH_TO_VIRTUAL_SCHEMAS_JAR, VIRTUAL_SCHEMAS_JAR_NAME_AND_VERSION);
        uploadDriverToBucket(bucket);

        final String content = "%scriptclass com.exasol.adapter.RequestDispatcher;\n" //
//                + "%jvmoption -agentlib:jdwp=transport=dt_socket,server=n,address=172.17.0.1:8000,suspend=y;\n"
                + "%jar /buckets/bfsdefault/default/" + VIRTUAL_SCHEMAS_JAR_NAME_AND_VERSION + ";\n" //
                + "%jar /buckets/bfsdefault/default/drivers/jdbc/" + DRIVER_NAME + ";\n";

        return adapterSchema.createAdapterScriptBuilder("ADAPTER_SCRIPT") //
                .language(Language.JAVA) //
                .content(content).build();
    }

    private static void uploadDriverToBucket(final Bucket bucket)
            throws InterruptedException, BucketAccessException, TimeoutException {
        bucket.uploadFile(Path.of("src/test/resources/integration/settings.cfg"), "drivers/jdbc/settings.cfg");
        bucket.uploadFile(Path.of("src/test/resources/integration/" + DRIVER_NAME), "drivers/jdbc/" + DRIVER_NAME);
        bucket.uploadFile(Path.of("src/test/resources/integration/" + DRIVER_NAME), DRIVER_NAME);
    }

    @AfterAll
    static void afterAll() throws SQLException {
        dropAll(adapterScript, adapterSchema);
        adapterScript = null;
        adapterSchema = null;
        connection.close();
    }

    @BeforeEach
    void beforeEach() {
        this.jdbcConnection = createAdapterConnectionDefinition();
        this.virtualSchema = null;
        esGateway = new ElasticSearchGateway(ES_CONTAINER.getHttpHostAddress());
    }

    @AfterEach
    void afterEach() {
        dropAll(this.virtualSchema, this.jdbcConnection);
        this.virtualSchema = null;
        this.jdbcConnection = null;
        esGateway.closeConnection();
    }

    private ConnectionDefinition createAdapterConnectionDefinition() {
        final String jdbcUrl = "jdbc:es://" + DOCKER_IP_ADDRESS + ":" + ES_CONTAINER.getMappedPort(9200) + "/";
        return objectFactory.createConnectionDefinition("JDBC_CONNECTION", jdbcUrl);
    }

    /**
     * Drop all given database object if it is not already assigned to {@code null}.
     * <p>
     * The method is {@code static} so that it can be used in {@code afterAll()} too.
     * </p>
     *
     * @param databaseObjects database objects to be dropped
     */
    private static void dropAll(final DatabaseObject... databaseObjects) {
        for (final DatabaseObject databaseObject : databaseObjects) {
            if (databaseObject != null) {
                databaseObject.drop();
            }
        }
    }

    @Test
    void testExample() throws IOException {
        final String indexName = "book";
        final Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("title", "fun_book");
        jsonMap.put("author.firstname", "marcelo");
        jsonMap.put("author.lastname", "ch");
        jsonMap.put("pages", 14);

        esGateway.indexDocument(indexName, jsonMap);

        assertVirtualTableContents(indexName, table("VARCHAR").row("Hello").row("world").matches());

//        final ResultSet resultSet = exasolTestDatabaseBuilder.getStatement()
//                .executeQuery("SELECT COUNT(*) as NUMBER_OF_BOOKS FROM BOOKS;");
//        resultSet.next();
//        final int number_of_books = resultSet.getInt("NUMBER_OF_BOOKS");
//        assertThat(number_of_books, equalTo(3));
    }

    private void assertVirtualTableContents(final String indexName, final Matcher<ResultSet> matcher) {
        final VirtualSchema virtualSchema = createVirtualSchema();
        try {
            assertThat(selectAllFromCorrespondingVirtualTable(virtualSchema, indexName), matcher);
        } catch (final SQLException exception) {
            fail("Unable to execute assertion query. Caused by: " + exception.getMessage());
        } finally {
            virtualSchema.drop();
        }
    }

    private VirtualSchema createVirtualSchema() {
        return objectFactory.createVirtualSchemaBuilder("VIRTUAL_SCHEMA_ES").dialectName(ES_DIALECT_NAME) //
                .adapterScript(adapterScript) //
                .connectionDefinition(this.jdbcConnection) //
                .build();
    }

    private ResultSet selectAllFromCorrespondingVirtualTable(final VirtualSchema virtualSchema, final String indexName)
            throws SQLException {
        return selectAllFrom(getVirtualTableName(virtualSchema, indexName));
    }

    private String getVirtualTableName(final VirtualSchema virtualSchema, final String indexName) {
        return virtualSchema.getFullyQualifiedName() + ".\"" + indexName + "\"";
    }

    private ResultSet selectAllFrom(final String tableName) throws SQLException {
        return query("SELECT * FROM " + tableName);
    }

    protected ResultSet query(final String sql) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }
}
