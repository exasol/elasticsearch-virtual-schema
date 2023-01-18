# Virtual Schema for Elasticsearch 2.1.0, released 2023-01-??

Code name: Enhanced Data Type Detection for Result Sets & Support for Elasticsearch 8.6

## Summary

Starting with version 7.1.14 Exasol database uses the capabilities reported by each virtual schema to provide select list data types for each push down request. Based on this information the JDBC virtual schemas no longer need to infer the data types of the result set by inspecting its values. Instead the JDBC virtual schemas can now use the information provided by the database.

This release provides enhanced data type detection for result sets by updating `virtual-schema-common-jdbc` to version [10.1.0](https://github.com/exasol/virtual-schema-common-jdbc/releases/tag/10.1.0).

This release also adds support for Elasticsearch version 8.6. If you run Elasticsearch version 7.x please use version 2.0.5 of the virtual schema.

There are some known issues with the current version. Please see the [user guide](https://github.com/exasol/elasticsearch-virtual-schema/blob/main/doc/user_guide/elasticsearch_sql_user_guide.md#known-issues) for details.

## Features

* #60: Added support for Elasticsearch 8

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:error-reporting-java:0.4.1` to `1.0.0`
* Updated `com.exasol:virtual-schema-common-jdbc:9.0.5` to `10.1.0`

### Test Dependency Updates

* Updated `co.elastic.clients:elasticsearch-java:7.17.5` to `8.6.0`
* Updated `com.exasol:exasol-testcontainers:6.1.2` to `6.5.0`
* Updated `com.exasol:hamcrest-resultset-matcher:1.5.1` to `1.5.2`
* Updated `com.exasol:test-db-builder-java:3.3.3` to `3.4.1`
* Updated `com.exasol:udf-debugging-java:0.6.4` to `0.6.6`
* Removed `commons-codec:commons-codec:1.15`
* Removed `org.apache.httpcomponents:httpclient:4.5.13`
* Updated `org.eclipse:yasson:3.0.0` to `3.0.2`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:7.17.5` to `8.6.0`
* Updated `org.junit.jupiter:junit-jupiter:5.9.0` to `5.9.2`
* Updated `org.mockito:mockito-junit-jupiter:4.6.1` to `5.0.0`
* Updated `org.testcontainers:elasticsearch:1.17.3` to `1.17.6`
* Updated `org.testcontainers:junit-jupiter:1.17.3` to `1.17.6`

### Plugin Dependency Updates

* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.4.0` to `0.4.2`
* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.1` to `1.2.1`
* Updated `com.exasol:project-keeper-maven-plugin:2.5.0` to `2.9.1`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.16`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.3.0` to `3.4.2`
* Updated `org.apache.maven.plugins:maven-dependency-plugin:3.3.0` to `2.8`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.1.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.0.0-M7`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.2` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.0.0-M7`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.3.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.13.0`
