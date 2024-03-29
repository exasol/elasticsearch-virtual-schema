# Virtual Schema for Elasticsearch 2.1.2, released 2023-09-29

Code name: Fix CVE-2023-42503 in test dependency

## Summary

This release fixes CVE-2023-42503 in test dependency `org.apache.commons:commons-compress`.

## Security

* #69: Fixed CVE-2023-42503 in test dependency `org.apache.commons:commons-compress`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:virtual-schema-common-jdbc:10.5.0` to `11.0.2`

### Test Dependency Updates

* Updated `co.elastic.clients:elasticsearch-java:8.7.1` to `8.10.2`
* Updated `com.exasol:exasol-testcontainers:6.5.2` to `6.6.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.0` to `1.6.1`
* Updated `com.exasol:test-db-builder-java:3.4.2` to `3.5.1`
* Updated `com.exasol:udf-debugging-java:0.6.8` to `0.6.11`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:8.7.1` to `8.10.2`
* Updated `org.junit.jupiter:junit-jupiter:5.9.3` to `5.10.0`
* Updated `org.mockito:mockito-junit-jupiter:5.3.1` to `5.5.0`
* Updated `org.slf4j:slf4j-jdk14:2.0.7` to `2.0.9`
* Updated `org.testcontainers:elasticsearch:1.18.0` to `1.19.0`
* Updated `org.testcontainers:junit-jupiter:1.18.0` to `1.19.0`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.3` to `1.3.0`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.7` to `2.9.12`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.5.0` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.3.0` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0` to `3.1.2`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0` to `3.1.2`
* Updated `org.basepom.maven:duplicate-finder-maven-plugin:1.5.1` to `2.0.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.4.1` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.15.0` to `2.16.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.9` to `0.8.10`
