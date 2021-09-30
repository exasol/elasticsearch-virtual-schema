# Virtual Schema for ElasticSearch 2.0.2, released 2021-09-30

Code name: Upgrade Elasticsearch to fix security issue

## Security Fixes

* #45: Upgraded Elasticsearch to version 7.15.0, fixing [CVE-2021-22147](https://ossindex.sonatype.org/vulnerability/a52f2ab6-086b-4285-a7a1-78ecdc6404ba?component-type=maven&component-name=org.elasticsearch.elasticsearch&utm_source=ossindex-client&utm_medium=integration&utm_content=1.1.1)

## Dependency Updates

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:4.0.0` to `5.1.0`
* Updated `com.exasol:hamcrest-resultset-matcher:1.4.1` to `1.5.0`
* Updated `org.elasticsearch.client:elasticsearch-rest-high-level-client:7.13.4` to `7.15.0`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:7.13.4` to `7.15.0`
* Updated `org.junit.jupiter:junit-jupiter:5.7.2` to `5.8.1`
* Updated `org.mockito:mockito-junit-jupiter:3.11.2` to `3.12.4`

### Plugin Dependency Updates

* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.3.1` to `0.4.0`
* Updated `com.exasol:error-code-crawler-maven-plugin:0.5.1` to `0.6.0`
* Updated `com.exasol:project-keeper-maven-plugin:0.10.0` to `1.0.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0-M3` to `3.0.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.7` to `2.8.1`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.5` to `0.8.7`
