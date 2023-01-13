# ElasticsearchSQL Dialect User Guide

[Elasticsearch](https://www.elastic.co/) is a distributed, open source search and analytics engine that allows to store and query documents in JSON format, and it provides a SQL dialect called [ElasticsearchSQL](https://www.elastic.co/what-is/elasticsearch-sql) for performing SQL queries.

This guide shows how to create and use a Virtual Schema in the Exasol database to query an Elasticsearch data source.

## Uploading the JDBC Driver to EXAOperation

First download the [Elasticsearch JDBC driver](https://www.elastic.co/downloads/jdbc-client).

1. [Create a bucket in BucketFS](https://docs.exasol.com/administration/on-premise/bucketfs/create_new_bucket_in_bucketfs_service.htm)
1. Upload the driver to BucketFS

**IMPORTANT**: Currently you have to **Disable Security Manager** for the driver if you want to connect to an Elasticsearch data source using the Elasticsearch Virtual Schema.
The reason is that the JDBC driver requires a JAVA permission which we do not grant by default.

**IMPORTANT**: Have in mind that the use of the Elasticsearch JDBC driver currently requires a license on the server, so this feature won't be available without it.

### Known Issue

The Elasticsearch JDBC driver in version 8.x changes behavior compared to 7.17.x. This will be addressed in [#60](https://github.com/exasol/elasticsearch-virtual-schema/issues/60). Until then we recommend using Elasticsearch version 7.17.5 for both the server and the JDBC driver.

## Installing the Adapter Script

Upload the latest available release of [Elasticsearch Virtual Schema adapter](https://github.com/exasol/elasticsearch-virtual-schema/releases) to Bucket FS.

Then create a schema to hold the adapter script.

```sql
CREATE SCHEMA <schema_name>;
```

The SQL statement below creates the adapter script, defines the Java class that serves as entry point and tells the UDF framework where to find the libraries (JAR files) for Virtual Schema and database driver.

```sql
CREATE OR REPLACE JAVA ADAPTER SCRIPT <schema_name>.<adapter_name> AS
  %scriptclass com.exasol.adapter.RequestDispatcher;
  %jar /buckets/<BFS service>/<bucket>/virtual-schema-dist-10.1.0-elasticsearch-2.1.0.jar;
  %jar /buckets/<BFS service>/<bucket>/x-pack-sql-jdbc-<elasticsearch_driver_version>.jar;
/
```

## Defining a Named Connection

Define the connection to Elasticsearch as shown below.

```sql
CREATE OR REPLACE CONNECTION <connection_name>
TO 'jdbc:es://<host>:<port>'
USER '<user>'
IDENTIFIED BY '<password>';
```

See the [Elasticsearch documentation](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/sql-jdbc.html#jdbc-setup) for details about the JDBC URL options.

## Creating a Virtual Schema

Below you see how an ElasticsearchSQL Virtual Schema is created.

```sql
CREATE VIRTUAL SCHEMA <elastic_search_virtual_schema_name>
	USING <schema_name>.<adapter_name>
	WITH
	CONNECTION_NAME = '<connection_name>'
	;
```

## Elasticsearch Identifiers

Exasol folds unquoted identifiers to upper case, but Elasticsearch identifiers are case sensitive. This means that if you don't quote the identifiers when performing a query from the Exasol database to the Elasticsearch Virtual Schema, it will fail as everything that is unquoted gets folded to upper case. That's why you should  **always quote the identifiers** when performing such queries, as follows:

```sql
SELECT "<identifier_name>" FROM "<elastic_search_virtual_schema_name>"."<table_name>";
```

### Nested Identifier conversion

Elasticsearch is a document based database. Among other things, this means you can upload documents with a nested structure fields, as any typical JSON file. For example, consider an Elasticsearch index named `book`, containing the following Joshua Bloch's Effective Java book brief description:

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

You can see the `author` field consists of the `first_name` and `last_name` fields. These are called "nested fields".

For referencing nested fields directly in an Elasticsearch data source, the dot (`.`) is used as identifier separator, so if you want to refer to the first name of the author, you would do it as `author.first_name`, as is in the following example query executed directly in the Elasticsearch database:

```sql
SELECT "author.first_name" FROM "book";
```

In Exasol however the dot (`.`) can not be used inside quoted identifiers, which means that `author.first_name` is not allowed. On the other hand, as explained above, you should always use quoted identifiers given the case sensitive nature of Elasticsearch.

To overcome this situation, every nested field from Elasticsearch is automatically converted to the virtual schema in the Exasol database using the slash (`/`) as identifier separator. Hence, `author.first_name` becomes `author/first_name`, so the previous example query becomes:

```sql
SELECT "author/first_name" FROM "<elastic_search_virtual_schema_name>"."book";
```

### Elasticsearch Mapping

In Elasticsearch, [mapping](https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping.html) is the process of defining how a document, and the fields it contains, are stored and indexed. Among other things, the mapping of documents includes the definition of the fields that can be used for performing queries over those documents, and more over these fields are going to be available to perform queries from the Exasol database using the Virtual Schema.

Elasticsearch allows [dynamic mapping](https://www.elastic.co/guide/en/elasticsearch/reference/current/dynamic-mapping.html), which means that fields and mapping types do not need to be defined before being used, and the new field names will be added automatically, just by indexing a document.

If the new fields are added after creating Virtual Schema, those fields will not be available, as the Virtual Schema is not up to date at this point, but it can be updated by executing the following command:

```sql
ALTER VIRTUAL SCHEMA "<elastic_search_virtual_schema_name>" REFRESH;
```

Given the previously described situation, we recommend to not use the dynamic mapping feature of Elasticsearch, and instead define the mappings explicitly.

### Known issues

At the moment for querying all the contents of a table in the virtual schema it is not possible to use the `*` wild card, as in:

```sql
SELECT * FROM "<elastic_search_virtual_schema_name>"."<table_name>";
```

Instead the columns to query needs to be explicitly specified, as in:

```sql
SELECT "<column_name_1>", "<column_name_2>", ..., "<column_name_n>" FROM "<elastic_search_virtual_schema_name>"."<table_name>";
```

A new feature to avoid this issue will be added in the future.
