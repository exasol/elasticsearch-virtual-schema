# Virtual Schema for ElasticSearch 2.0.3, released 2021-12-14

Code name: Dependency Updates

## Summary

In this release we updated the project's dependencies. By that we fixed the transitive dependency [CVE-2021-44228](https://nvd.nist.gov/vuln/detail/CVE-2021-44228) in log4j. The vulnerability only affected test code.

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:error-reporting-java:0.4.0` to `0.4.1`
* Updated `com.exasol:virtual-schema-common-jdbc:9.0.3` to `9.0.4`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:5.1.0` to `5.1.1`
* Updated `com.exasol:hamcrest-resultset-matcher:1.5.0` to `1.5.1`
* Updated `com.exasol:udf-debugging-java:0.4.0` to `0.4.1`
* Removed `junit:junit:4.13.2`
* Added `org.apache.logging.log4j:log4j-api:2.16.0`
* Updated `org.elasticsearch.client:elasticsearch-rest-high-level-client:7.15.0` to `7.16.1`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:7.15.0` to `7.16.1`
* Updated `org.junit.jupiter:junit-jupiter:5.8.1` to `5.8.2`
* Updated `org.mockito:mockito-junit-jupiter:3.12.4` to `4.1.0`
* Updated `org.testcontainers:elasticsearch:1.16.0` to `1.16.2`
* Updated `org.testcontainers:junit-jupiter:1.16.0` to `1.16.2`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:0.6.0` to `0.7.1`
* Updated `com.exasol:project-keeper-maven-plugin:1.0.0` to `1.3.4`
