# Virtual Schema for ElasticSearch 2.0.4, released 2021-12-23

Code name: Fixed Log4J CVE-2021-45105 vulnerability

## Summary

In this release we fixed Log4J [`CVE-2021-45105`](https://github.com/advisories/GHSA-p6xc-xr62-6r2g) vulnerability.

## Bug Fixes

* #51: Updated log4j dependency to 2.17.0 version

## Dependency Updates

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:5.1.1` to `6.0.0`
* Updated `com.exasol:test-db-builder-java:3.2.1` to `3.3.0`
* Updated `com.exasol:udf-debugging-java:0.4.1` to `0.5.0`
* Updated `org.apache.logging.log4j:log4j-api:2.16.0` to `2.17.1`
* Updated `org.elasticsearch.client:elasticsearch-rest-high-level-client:7.16.1` to `7.17.0`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:7.16.1` to `7.17.0`
* Updated `org.mockito:mockito-junit-jupiter:4.1.0` to `4.3.1`
* Updated `org.testcontainers:elasticsearch:1.16.2` to `1.16.3`
* Updated `org.testcontainers:junit-jupiter:1.16.2` to `1.16.3`

### Plugin Dependency Updates

* Updated `io.github.zlika:reproducible-build-maven-plugin:0.13` to `0.15`
* Updated `org.apache.maven.plugins:maven-clean-plugin:2.5` to `3.1.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.8.1` to `3.10.0`
* Updated `org.apache.maven.plugins:maven-dependency-plugin:2.8` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-deploy-plugin:2.7` to `2.8.2`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.4` to `2.5.2`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.0` to `3.2.2`
* Updated `org.apache.maven.plugins:maven-resources-plugin:2.6` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.3` to `3.10.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.8.1` to `2.9.0`
* Updated `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.1.0` to `3.2.0`
