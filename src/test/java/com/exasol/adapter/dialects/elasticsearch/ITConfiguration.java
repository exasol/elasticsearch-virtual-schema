package com.exasol.adapter.dialects.elasticsearch;

import java.nio.file.Path;

final class ITConfiguration {
    private static final String DEFAULT_EXASOL_DOCKER_IMAGE_REFERENCE = "7.1.3";
    static final String ELASTICSEARCH_DOCKER_IMAGE_REFERENCE = "docker.elastic.co/elasticsearch/elasticsearch:7.16.1";
    static final String VIRTUAL_SCHEMAS_JAR_NAME_AND_VERSION = "virtual-schema-dist-9.0.4-elasticsearch-2.0.4.jar";
    static final String TEST_RESOURCES_PATH = "src/test/resources/integration/";
    static final String JDBC_DRIVER_NAME = "x-pack-sql-jdbc.jar";
    static final String SETTINGS_FILE_NAME = "settings.cfg";
    static final String DOCKER_IP_ADDRESS = "172.17.0.1";
    static final String DEFAULT_BUCKET_PATH = "/buckets/bfsdefault/default/";
    static final String JDBC_DRIVERS_IN_BUCKET_PATH = "drivers/jdbc/";
    static final Path VIRTUAL_SCHEMAS_JAR_PATH = Path.of("target", VIRTUAL_SCHEMAS_JAR_NAME_AND_VERSION);
    static final Path JDBC_DRIVER_PATH = Path.of("target/elasticsearch-driver/" + JDBC_DRIVER_NAME);
    static final Path SETTINGS_FILE_PATH = Path.of(TEST_RESOURCES_PATH + SETTINGS_FILE_NAME);

    private ITConfiguration() {
        // prevent instantiation
    }

    /**
     * Get the Exasol {@code docker-db} image reference.
     * <p>
     * This reference can be overridden by setting the Java property {@code com.exasol.dockerdb.image}. If the property
     * is not set, then the default reference provided with the integration tests is used instead.
     * </p>
     *
     * @return reference to the Exasol {@code docker-db} image.
     */
    public static String getExasolDockerImageReference() {
        return System.getProperty("com.exasol.dockerdb.image", DEFAULT_EXASOL_DOCKER_IMAGE_REFERENCE);
    }
}
