# Virtual Schema for ElasticSearch 2.0.0, released 2021-??-??

Code name:

## Summary

The `SQL_DIALECT` property used when executing a `CREATE VIRTUAL SCHEMA` from the Exasol database is obsolete from this version. Please, do not provide this property anymore.

## Features / Enhancements

* #23: Add support for `CHR` scalar function
* #24: Add support for `LN` scalar function

## Runtime Dependencies

* Updated `com.exasol:virtual-schema-common-jdbc:8.0.0` to `9.0.1`

## Test Dependencies

* Updated `org.elasticsearch.client:elasticsearch-rest-high-level-client:7.10.1` to `7.10.2`
* Updated `org.elasticsearch.plugin:x-pack-sql-jdbc:7.10.1` to `7.10.2`