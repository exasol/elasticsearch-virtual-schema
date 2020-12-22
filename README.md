# ElasticSearch Virtual Schema

[![Build Status](https://api.travis-ci.com/exasol/elasticsearch-virtual-schema.svg?branch=main)](https://travis-ci.com/exasol/elasticsearch-virtual-schema)
[![Maven Central](https://img.shields.io/maven-central/v/com.exasol/elasticsearch-virtual-schema)](https://search.maven.org/artifact/com.exasol/elasticsearch-virtual-schema)

SonarCloud results:

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=code_smells)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=coverage)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)

An implementation of the [ElasticSearchSQL](https://www.elastic.co/what-is/elasticsearch-sql) dialect for the [Exasol Virtual Schema JDBC][virtual-schema-common-jdbc] project.
Use this dialect if you want to create a Virtual Schema based on an ElasticSearch database.

If you want to set up a Virtual Schema for a different database system, please head over to the [Virtual Schemas Repository][virtual-schemas].

## Features

* Access an ElasticSearch database from an Exasol database, using Virtual Schema.

## Table of Contents

### Information for Users

* [ElasticSearchSQL dialect](doc/dialects/elasticsearchsql.md)
* [User Guide][user-guide]
* [Changelog](doc/changes/changelog.md)

Find all the documentation in the [Virtual Schemas project][vs-doc].

## Information for Developers 

* [Virtual Schema API Documentation][vs-api]

### Run Time Dependencies

Running the Virtual Schema requires a Java Runtime version 11 or later.

| Dependency                                                         | Purpose                                                | License                       |
|--------------------------------------------------------------------|--------------------------------------------------------|-------------------------------|
| [Exasol Virtual Schema JDBC][virtual-schema-common-jdbc]           | Common JDBC functions for Virtual Schemas adapters     | MIT License                   |
| [ElasticSearch JDBC Driver][elasticsearch-jdbc-driver]             | JDBC driver for ElasticSeach database                  | Elastic License               |
| [Exasol Error Reporting][exasol-error-reporting]                   | Creating unified error messages                        | MIT License                   |

### Test Dependencies

| Dependency                                                         | Purpose                                                | License                       |
|--------------------------------------------------------------------|--------------------------------------------------------|-------------------------------|
| [Java Hamcrest](http://hamcrest.org/JavaHamcrest/)                 | Checking for conditions in code via matchers           | BSD License                   |
| [JUnit](https://junit.org/junit5)                                  | Unit testing framework                                 | Eclipse Public License 1.0    |
| [Mockito](http://site.mockito.org/)                                | Mocking framework                                      | MIT License                   |
| [ElasticSearch Rest Client][elasticsearch-rest-high-level-client]  | ElasticSearch database API                             | Apache License 2.0            |
| [Testcontainers](https://www.testcontainers.org/)                  | Container-based integration tests                      | MIT License                   |
| [Exasol Testcontainers][exasol-testcontainers]                     | Exasol extension for the Testcontainers framework      | MIT License                   |
| [Test Database Builder][test-db-builder]                           | Fluent database interfaces for testing                 | MIT License                   |
| [Exasol Hamcrest Result Set Matcher][exasol-hamcrest]              | Hamcrest result set matcher for testing                | MIT License                   |
| [Exasol UDF Debugging][udf-debugging-java]                         | Debugging UDFs in testing                              | MIT License                   |
| [Apache Http Client][apache-httpcomponents-httpclient]             | Used in the ElasticSearch API, fixes CVE-2020-13956    | Apache License 2.0            |


### Maven Plug-ins

| Plug-in                                                            | Purpose                                                | License                       |
|--------------------------------------------------------------------|--------------------------------------------------------|-------------------------------|
| [Maven Jacoco Plugin][maven-jacoco-plugin]                         | Code coverage metering                                 | Eclipse Public License 2.0    |
| [Maven Surefire Plugin][maven-surefire-plugin]                     | Unit testing                                           | Apache License 2.0            |
| [Maven Compiler Plugin][maven-compiler-plugin]                     | Setting required Java version                          | Apache License 2.0            |
| [Maven Assembly Plugin][maven-assembly-plugin]                     | Creating JAR                                           | Apache License 2.0            |
| [Maven Failsafe Plugin][maven-failsafe-plugin]                     | Integration testing                                    | Apache License 2.0            |
| [Versions Maven Plugin][versions-maven-plugin]                     | Checking if dependencies updates are available         | Apache License 2.0            |
| [Maven Enforcer Plugin][maven-enforcer-plugin]                     | Controlling environment constants                      | Apache License 2.0            |
| [Maven Dependency Plugin][maven-dependency-plugin]                 | Accessing to test dependencies                         | Apache License 2.0            |
| [Artifact Reference Checker Plugin][artifact-ref-checker-plugin]   | Check if artifact is referenced with correct version   | MIT License                   |
| [Project Keeper Maven Plugin][project-keeper-maven-plugin]         | Checking project structure                             | MIT License                   |
| [Sonatype OSS Index Maven Plugin][sonatype-oss-index-maven-plugin] | Checking dependencies vulnerability                    | ASL2                          |

[virtual-schema-common-jdbc]: https://github.com/exasol/virtual-schema-common-jdbc
[elasticsearch-jdbc-driver]: https://www.elastic.co/downloads/jdbc-client
[exasol-error-reporting]: https://github.com/exasol/error-reporting-java/

[elasticsearch-rest-high-level-client]: https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high.html
[exasol-testcontainers]: https://github.com/exasol/exasol-testcontainers
[test-db-builder]: https://github.com/exasol/test-db-builder/
[exasol-hamcrest]: https://github.com/exasol/hamcrest-resultset-matcher
[udf-debugging-java]: https://github.com/exasol/udf-debugging-java
[apache-httpcomponents-httpclient]: https://hc.apache.org/httpcomponents-client-ga/

[maven-jacoco-plugin]: https://www.eclemma.org/jacoco/trunk/doc/maven.html
[maven-surefire-plugin]: https://maven.apache.org/surefire/maven-surefire-plugin/
[maven-compiler-plugin]: https://maven.apache.org/plugins/maven-compiler-plugin/
[maven-assembly-plugin]: https://maven.apache.org/plugins/maven-assembly-plugin/
[maven-failsafe-plugin]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[versions-maven-plugin]: https://www.mojohaus.org/versions-maven-plugin/
[maven-enforcer-plugin]: http://maven.apache.org/enforcer/maven-enforcer-plugin/
[artifact-ref-checker-plugin]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[maven-dependency-plugin]: https://maven.apache.org/plugins/maven-dependency-plugin/
[project-keeper-maven-plugin]: https://github.com/exasol/project-keeper-maven-plugin
[sonatype-oss-index-maven-plugin]: https://sonatype.github.io/ossindex-maven/maven-plugin/

[user-guide]: https://docs.exasol.com/database_concepts/virtual_schemas.htm
[virtual-schemas]: https://github.com/exasol/virtual-schemas
[vs-api]: https://github.com/exasol/virtual-schema-common-java/blob/master/doc/development/api/virtual_schema_api.md
[vs-doc]: https://github.com/exasol/virtual-schemas/tree/master/doc
