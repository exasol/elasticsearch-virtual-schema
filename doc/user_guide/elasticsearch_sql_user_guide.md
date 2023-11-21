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
  %jar /buckets/<BFS service>/<bucket>/virtual-schema-dist-11.0.2-elasticsearch-2.1.3.jar;
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

See the [Elasticsearch documentation](https://www.elastic.co/guide/en/elasticsearch/reference/8.6/sql-jdbc.html#jdbc-setup) for details about the JDBC URL options.

## Creating a Virtual Schema

Below you see how an ElasticsearchSQL Virtual Schema is created.

```sql
CREATE VIRTUAL SCHEMA <elastic_search_virtual_schema_name>
    USING <schema_name>.<adapter_name>
    WITH
    CONNECTION_NAME = '<connection_name>';
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

## Known issues

### `SELECT *` returns additional keyword column

Querying all the contents of a table in the virtual schema with the `*` wild card will return an additional column.

When you index this document

```json
{
    "c1" : "str",
    "c2" : 42,
    "c3" : 3.14
}
```

The query `SELECT * FROM "<elastic_search_virtual_schema_name>"."<table_name>"` will return four columns: `c1`, `c1/keyword`, `c2` and `c3`. Columns `c1` and `c1/keyword` will have the same content.

As a workaround please explicitly specify the selected columns, as in:

```sql
SELECT "<column_name_1>", "<column_name_2>", ..., "<column_name_n>" FROM "<elastic_search_virtual_schema_name>"."<table_name>";
```

### Conversion of numbers may return approximated results

When executing a query like this in the virtual schema it may return approximated numerical results:

```sql
SELECT TRUNC("FIELD", 2) FROM VIRTUAL_SCHEMA."index";
```

For input value 123.456 this will return 123.44999694824219 instead of the expected 123.45.

### Conversion of numbers may return invalid results

The `EXTRACT SECOND FROM` and other scalar function may return wrong values, e.g. the following statement

```sql
SELECT EXTRACT(SECOND FROM CAST('2018-02-19 10:23:27' AS TIMESTAMP))
FROM VIRTUAL_SCHEMA."index";
```

will return 0.027 instead of 27. This is a known issue in Exasol 7.1.17. See also [issue #65](https://github.com/exasol/elasticsearch-virtual-schema/issues/65).

### Conversion of numbers may fail

The `FLOOR` and `CEIL` scalar functions may fail with an error message. Query `SELECT FLOOR("FIELD") FROM VIRTUAL_SCHEMA."index"` will fail with the following error message:

```
ETL-1299: Failed to create transformator for column=0 (starting from 0 for selected columns) [ETL-1202: Not implemented - Transformation for this combination of column types is not possible in this version. A solution for this problem can be perhaps the conversion in another datatype in the database. Otherwise please contact support for additional information]
```

This is a known issue in Exasol 7.1.17. See also [issue #66](https://github.com/exasol/elasticsearch-virtual-schema/issues/66).

### Adapter Property `IMPORT_DATA_TYPES` not supported

Due to restrictions of the Elasticsearch 8 JDBC driver the adapter property `IMPORT_DATA_TYPES` does not support the option `FROM_RESULT_SET`. Only the default value `EXASOL_CALCULATED` is supported. For details please see [adapter Properties for JDBC-Based Virtual Schemas](https://github.com/exasol/virtual-schema-common-jdbc/blob/main/README.md#adapter-properties-for-jdbc-based-virtual-schemas).

Background: The Elasticsearch 8 JDBC driver does not return Metadata for a prepared statement without executing it. So it is not possible for the adapter to determine the column types of an Elasticsearch SQL query and it will fail with the following error message:

```
VM error: F-UDF-CL-LIB-1126: F-UDF-CL-SL-JAVA-1006: F-UDF-CL-SL-JAVA-1026:
com.exasol.ExaUDFException: F-UDF-CL-SL-JAVA-1068: Exception during singleCall adapterCall
com.exasol.adapter.jdbc.RemoteMetadataReaderException: F-VSCJDBC-34: Metadata is missing in the ResultSet. This can happen if the generated query was incorrect, but the JDBC driver didn't throw an exception. This is an internal error that should not happen. Please report it by opening a GitHub issue.
```
