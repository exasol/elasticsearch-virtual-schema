# Virtual Schema for ElasticSearch 2.0.5, released 2022-08-02

Code name: Upgrade dependencies

## Summary

This release fixes the following vulnerabilities:

* io.netty:netty-common:jar:4.1.72.Final in test
    * CVE-2022-24823, severity CWE-378: Creation of Temporary File With Insecure Permissions (5.5)
* io.netty:netty-handler:jar:4.1.72.Final in test
    * sonatype-2020-0026: 1 vulnerability (6.5)
* org.apache.xmlrpc:xmlrpc-common:jar:3.1.3 in test
    * CVE-2016-5003, severity CWE-502: Deserialization of Untrusted Data (9.8)
    * CVE-2016-5002, severity CWE-611: Improper Restriction of XML External Entity Reference ('XXE') (7.8)
* com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:jar:2.10.4 in test
    * CVE-2020-28491, severity CWE-770: Allocation of Resources Without Limits or Throttling (7.5)
* commons-codec:commons-codec:jar:1.11 in test
    * sonatype-2012-0050: 1 vulnerability (5.3)
* org.apache.xmlrpc:xmlrpc-client:jar:3.1.3 in test
    * CVE-2016-5004, severity CWE-400: Uncontrolled Resource Consumption ('Resource Exhaustion') (6.5)

## Bug Fixes

* #53: Updated Log4j library to 2.17.1 version
* #56: Fixed vulnerabilities in dependencies

## Refactoring

* #58: Replace deprecated Elasticsearch High Level REST Client

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:virtual-schema-common-jdbc:9.0.4` to `9.0.5`

### Test Dependency Updates

* Added `co.elastic.clients:elasticsearch-java:8.3.3`
* Updated `com.exasol:exasol-testcontainers:5.1.1` to `6.1.2`
* Updated `com.exasol:test-db-builder-java:3.2.1` to `3.3.3`
* Updated `com.exasol:udf-debugging-java:0.4.1` to `0.6.4`
* Added `commons-codec:commons-codec:1.15`
* Removed `org.apache.logging.log4j:log4j-api:2.17.0`
* Added `org.eclipse:yasson:3.0.0`
* Removed `org.elasticsearch.client:elasticsearch-rest-high-level-client:7.16.2`
* Removed `org.elasticsearch.plugin:x-pack-sql-jdbc:7.16.2`
* Updated `org.junit.jupiter:junit-jupiter:5.8.2` to `5.9.0`
* Updated `org.mockito:mockito-junit-jupiter:4.2.0` to `4.6.1`
* Updated `org.testcontainers:elasticsearch:1.16.2` to `1.17.3`
* Updated `org.testcontainers:junit-jupiter:1.16.2` to `1.17.3`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:0.7.1` to `1.1.1`
* Updated `com.exasol:project-keeper-maven-plugin:1.3.4` to `2.5.0`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.14` to `0.15`
* Updated `org.apache.maven.plugins:maven-clean-plugin:3.1.0` to `2.5`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.8.1` to `3.10.1`
* Updated `org.apache.maven.plugins:maven-dependency-plugin:3.2.0` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-deploy-plugin:2.8.2` to `2.7`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M3` to `3.0.0-M5`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.5.2` to `2.4`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.0` to `3.2.2`
* Updated `org.apache.maven.plugins:maven-resources-plugin:3.2.0` to `2.6`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.9.1` to `3.3`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M3` to `3.0.0-M5`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.8.1` to `2.10.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.7` to `0.8.8`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Updated `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.1.0` to `3.2.0`
