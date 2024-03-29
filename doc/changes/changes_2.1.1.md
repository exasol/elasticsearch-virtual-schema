# Virtual Schema for Elasticsearch 2.1.1, released 2023-05-09

Code name: Update Dependencies on Top of 2.1.0

## Summary

This release updates dependency `virtual-schema-common-jdbc` which adds support for the new adapter property [`MAX_TABLE_COUNT`](https://github.com/exasol/virtual-schema-common-jdbc#property-max_table_count) and fixes ambiguous results by escaping SQL wildcards such as underscore `_` and percent `%` in names of catalogs, schemas, and tables when retrieving column metadata from JDBC driver.

## Refactoring

* #62: Renamed error codes from VS-ES to VSES

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:error-reporting-java:1.0.0` to `1.0.1`
* Updated `com.exasol:virtual-schema-common-jdbc:10.1.0` to `10.5.0`

### Test Dependency Updates

* Updated `co.elastic.clients:elasticsearch-java:8.6.0` to `8.7.1`
* Updated `com.exasol:exasol-testcontainers:6.5.0` to `6.5.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.5.2` to `1.6.0`
* Updated `com.exasol:test-db-builder-java:3.4.1` to `3.4.2`
* Updated `com.exasol:udf-debugging-java:0.6.6` to `0.6.8`
* Updated `org.eclipse:yasson:3.0.2` to `3.0.3`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:8.6.0` to `8.7.1`
* Updated `org.junit.jupiter:junit-jupiter:5.9.2` to `5.9.3`
* Updated `org.mockito:mockito-junit-jupiter:5.0.0` to `5.3.1`
* Added `org.slf4j:slf4j-jdk14:2.0.7`
* Updated `org.testcontainers:elasticsearch:1.17.6` to `1.18.0`
* Updated `org.testcontainers:junit-jupiter:1.17.6` to `1.18.0`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.1` to `1.2.3`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.1` to `2.9.7`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.4.2` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.1.0` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M7` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M7` to `3.0.0`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:1.5.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.3.0` to `1.4.1`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.13.0` to `2.15.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.9`
