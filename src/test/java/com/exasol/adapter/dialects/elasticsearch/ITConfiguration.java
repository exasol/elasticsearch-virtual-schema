package com.exasol.adapter.dialects.elasticsearch;

import java.nio.file.Path;

public final class ITConfiguration {
    private static final String EXASOL_DOCKER_IMAGE_REFERENCE = "7.0.2";
    private static final String ES_DEFAULT_DOCKER_IMAGE_REFERENCE = "docker.elastic.co/elasticsearch/elasticsearch:7.9.3";
    public static final String VIRTUAL_SCHEMAS_JAR_NAME_AND_VERSION = "virtual-schema-dist-7.0.0-elasticsearch-0.1.0.jar";
    public static final Path PATH_TO_VIRTUAL_SCHEMAS_JAR = Path.of("target", VIRTUAL_SCHEMAS_JAR_NAME_AND_VERSION);
    public static final String DRIVER_NAME = "x-pack-sql-jdbc-7.9.3.jar";
    public static final String DOCKER_IP_ADDRESS = "172.17.0.1";

    private ITConfiguration() {
        // prevent instantiation
    }

    /**
     * Get the {@code docker-db} image reference.
     * <p>
     * This reference can be overridden by setting the Java property {@code com.exasol.dockerdb.image}. If the property
     * is not set, then the default reference provided with the integration tests is used instead.
     * </p>
     *
     * @return reference to the {@code docker-db} image.
     */
    public static String getExasolDockerImageReference() {
        return System.getProperty("com.exasol.dockerdb.image", EXASOL_DOCKER_IMAGE_REFERENCE);
    }

    /**
     * Get the {@code docker-db} image reference.
     * <p>
     * This reference can be overridden by setting the Java property {@code com.exasol.dockerdb.image}. If the property
     * is not set, then the default reference provided with the integration tests is used instead.
     * </p>
     *
     * @return reference to the {@code docker-db} image.
     */
    public static String getElasticSearchDockerImageReference() {
        return System.getProperty("com.exasol.dockerdb.image", ES_DEFAULT_DOCKER_IMAGE_REFERENCE);
    }
}
