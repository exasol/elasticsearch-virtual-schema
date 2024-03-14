# Virtual Schema for Elasticsearch 2.1.4, released 2024-03-14

Code name: Fixed vulnerabilities CVE-2024-25710 and CVE-2024-26308 in test dependencies

## Summary

This is a security release in which we updated test dependency `com.exasol:exasol-test-setup-abstraction-java` to fix vulnerabilities CVE-2024-25710 and CVE-2024-26308 in its transitive dependencies.

## Security

* #73: Fixed vulnerability CVE-2024-25710 by updating test dependency
* #74: Fixed vulnerability CVE-2024-26308 by updating test dependency

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:virtual-schema-common-jdbc:11.0.2` to `12.0.0`

### Test Dependency Updates

* Updated `co.elastic.clients:elasticsearch-java:8.11.1` to `8.12.2`
* Updated `com.exasol:exasol-testcontainers:6.6.3` to `7.0.1`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.3` to `1.6.5`
* Updated `com.exasol:test-db-builder-java:3.5.2` to `3.5.4`
* Updated `com.exasol:udf-debugging-java:0.6.11` to `0.6.12`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:8.11.1` to `8.12.2`
* Updated `org.junit.jupiter:junit-jupiter:5.10.1` to `5.10.2`
* Updated `org.mockito:mockito-junit-jupiter:5.7.0` to `5.11.0`
* Updated `org.slf4j:slf4j-jdk14:2.0.9` to `2.0.12`
* Updated `org.testcontainers:elasticsearch:1.19.2` to `1.19.7`
* Updated `org.testcontainers:junit-jupiter:1.19.2` to `1.19.7`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.1` to `2.0.1`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.16` to `4.2.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.12.1`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.2.2` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.2` to `3.2.5`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.1` to `2.16.2`
