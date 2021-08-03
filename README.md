# ElasticSearch Virtual Schema

[![Build Status](https://github.com/exasol/elasticsearch-virtual-schema/actions/workflows/ci-build.yml/badge.svg)](https://github.com/exasol/elasticsearch-virtual-schema/actions/workflows/ci-build.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=code_smells)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=coverage)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aelasticsearch-virtual-schema&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.exasol%3Aelasticsearch-virtual-schema)'

# Overview

The **ElasticSearch Virtual Schema** provides an abstraction layer that makes an external [ElasticSearch](https://www.elastic.co/) data source accessible from an Exasol database through regular SQL commands. The contents of the external ElasticSearch data source are mapped to virtual tables which look like and can be queried as any regular Exasol table.

If you want to set up a Virtual Schema for a different database system, please head over to the [Virtual Schemas Repository][virtual-schemas].

## Features

* Access an ElasticSearch data source in read only mode from an Exasol database, using Virtual Schema.

## Table of Contents

### Information for Users

* [Virtual Schemas User Guide][user-guide]
* [ElasticSearchSQL Dialect User Guide](doc/user_guide/elasticsearch_sql_user_guide.md)
* [Changelog](doc/changes/changelog.md)
* [Dependencies](dependencies.md)

Find all the documentation in the [Virtual Schemas project][vs-doc].

## Information for Developers

* [Virtual Schema API Documentation][vs-api]

<!-- @formatter:off -->
[virtual-schema-common-jdbc]: https://github.com/exasol/virtual-schema-common-jdbc
[user-guide]: https://docs.exasol.com/database_concepts/virtual_schemas.htm
[virtual-schemas]: https://github.com/exasol/virtual-schemas
[vs-api]: https://github.com/exasol/virtual-schema-common-java/blob/master/doc/development/api/virtual_schema_api.md
[vs-doc]: https://github.com/exasol/virtual-schemas/tree/master/doc
