# Virtual Schema for ElasticSearch 2.0.4, released 2021-12-23

Code name: Fixed Log4J CVE-2021-45105 vulnerability

## Summary

In this release we fixed Log4J [`CVE-2021-45105`](https://github.com/advisories/GHSA-p6xc-xr62-6r2g) vulnerability.

## Bug Fixes

* #51: Updated log4j dependency to 2.17.0 version

## Dependency Updates

### Test Dependency Updates

* Updated `org.apache.logging.log4j:log4j-api:2.16.0` to `2.17.0`
* Updated `org.elasticsearch.client:elasticsearch-rest-high-level-client:7.16.1` to `7.16.2`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:7.16.1` to `7.16.2`
* Updated `org.mockito:mockito-junit-jupiter:4.1.0` to `4.2.0`

### Plugin Dependency Updates

* Updated `io.github.zlika:reproducible-build-maven-plugin:0.13` to `0.14`
* Updated `org.apache.maven.plugins:maven-clean-plugin:2.5` to `3.1.0`
* Updated `org.apache.maven.plugins:maven-dependency-plugin:2.8` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-deploy-plugin:2.7` to `2.8.2`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.4` to `2.5.2`
* Updated `org.apache.maven.plugins:maven-resources-plugin:2.6` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.3` to `3.9.1`
