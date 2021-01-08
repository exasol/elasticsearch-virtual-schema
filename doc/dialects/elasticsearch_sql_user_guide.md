 ElasticSearchSQL Dialect User Guide

[ElasticSearch](https://www.elastic.co/) is a distributed, open source search and analytics engine that allows to store and query documents in JSON format, and it provides a SQL dialect called [ElasticSearchSQL](https://www.elastic.co/what-is/elasticsearch-sql) for performing SQL queries.

This guide shows how to create and use a Virtual Schema in the Exasol database to query an ElasticSearch data source.

## Uploading the JDBC Driver to EXAOperation

First download the [ElasticSearch JDBC driver](https://www.elastic.co/downloads/jdbc-client).

1. [Create a bucket in BucketFS](https://docs.exasol.com/administration/on-premise/bucketfs/create_new_bucket_in_bucketfs_service.htm)
1. Upload the driver to BucketFS

**IMPORTANT**: Currently you have to **Disable Security Manager** for the driver if you want to connect to an ElasticSearch data source using the ElasticSearch Virtual Schema.
The reason is that the JDBC driver requires a JAVA permission which we do not grant by default.

**IMPORTANT**: Have in mind that the use of the ElasticSearch JDBC driver currently requires a license, so this feature won't be available without it.

## Installing the Adapter Script

Upload the latest available release of [ElasticSearch Virtual Schema adapter](https://github.com/exasol/elasticsearch-virtual-schema/releases) to Bucket FS.

Then create a schema to hold the adapter script.

```sql
CREATE SCHEMA <schema_name>;
```

The SQL statement below creates the adapter script, defines the Java class that serves as entry point and tells the UDF framework where to find the libraries (JAR files) for Virtual Schema and database driver.

```sql
CREATE OR REPLACE JAVA ADAPTER SCRIPT <schema_name>.<adapter_name> AS
  %scriptclass com.exasol.adapter.RequestDispatcher;
  %jar /buckets/<BFS service>/<bucket>/virtual-schema-dist-8.0.0-elasticsearch-1.0.0.jar;
  %jar /buckets/<BFS service>/<bucket>/x-pack-sql-jdbc-<elasticsearch_driver_version>.jar;
/
```

## Defining a Named Connection

Define the connection to ElasticSearch as shown below.

```sql
CREATE OR REPLACE CONNECTION <connection_name>
TO 'jdbc:es://<host>:<port>'
USER '<user>'
IDENTIFIED BY '<password>';
```

## Creating a Virtual Schema

Below you see how an ElasticSearchSQL Virtual Schema is created.

```sql
CREATE VIRTUAL SCHEMA <elastic_search_virtual_schema_name>
	USING <schema_name>.<adapter_name>
	WITH
	SQL_DIALECT = 'ES'
	CONNECTION_NAME = '<connection_name>'
	;
```

## ElasticSearch Identifiers

Exasol folds unquoted identifiers to upper case, but ElasticSearch identifiers are case sensitive. This means that if you don't quote the identifiers when performing a query from the Exasol database to the ElasticSearch Virtual Schema, it will fail as everything that is unquoted gets folded to upper case. That's why you should  **always quote the identifiers** when performing such queries, as follows:

```sql
SELECT "<identifier_name>" FROM "<elastic_search_virtual_schema_name>"."<table_name>";
```

### Nested Identifier conversion

ElasticSearch is a document based database. Among other things, it means you can upload documents with a nested structure fields, as any typical JSON file. For example, consider an ElasticSearch index named `book`, containing the following Joshua Bloch's Effective Java book brief description:

```json
{
	"book_title": "Effective Java",
	"pages_count": 377,
	"author": {
			"first_name": "Joshua",
			"last_name": "Bloch"
	}
}
```

You can see the `Author` field consists of the `first_name` and `last_name` fields. These are called "nested fields".

For referencing nested fields directly in an ElasticSearch data source, the dot(.) is used as identifier separator, so if you want to refer to the first name of the author, you would do it as `author.first_name`, as is in the following example query executed directly in the ElasticSearch database:

```sql
SELECT "author.first_name" FROM "book";
```

In Exasol however the dot(.) can not be used inside quoted identifiers, which means that `author.first_name` is not allowed. On the other hand, as explained above, you should always use quoted identifiers given the case sensitive nature of ElasticSearch.

To overcome this situation, every nested field from ElasticSearch is automatically converted to the virtual schema in the Exasol database using the slash(/) as identifier separator. Hence, `author.first_name` becomes `author/first_name`, so the previous example query becomes:

```sql
SELECT "author/first_name" FROM "<elastic_search_virtual_schema_name>"."book";
```

### ElasticSearch Mapping

In ElasticSearch, [mapping](https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping.html) is the process of defining how a document, and the fields it contains, are stored and indexed. Among other things, the mapping of documents includes the definition of the fields that can be used for performing queries over those documents, and more over these fields are going to be available to perform queries from the Exasol database using the VirtualSchema.

ElasticSearch allows [dynamic mapping](https://www.elastic.co/guide/en/elasticsearch/reference/current/dynamic-mapping.html), which means that fields and mapping types do not need to be defined before being used, and the new field names will be added automatically, just by indexing a document.

If the new fields are added after creating VirtualSchema, those fields will not be available, as the VirtualSchema is not up to date at this point, but it can be updated by executing the following command:

```sql
ALTER VIRTUAL SCHEMA "<elastic_search_virtual_schema_name>" REFRESH;
```

Given the previously described situation, we recommend to not use the dynamic mapping feature of ElasticSearch, and instead define the mappings explicitly.

### Known issues

At the moment for querying all the contents of a table in the virtual schema it is not possible to use the `*` wild card, as in:

```sql
SELECT * FROM "<elastic_search_virtual_schema_name>"."<table_name>";
```

Instead the columns to query needs to be explicitly specified, as in:

```sql
SELECT "<column_name_1>","<column_name_2>",...,"<column_name_n>" FROM "<elastic_search_virtual_schema_name>"."<table_name>";
```

A new feature to avoid this issue will be added in the future.