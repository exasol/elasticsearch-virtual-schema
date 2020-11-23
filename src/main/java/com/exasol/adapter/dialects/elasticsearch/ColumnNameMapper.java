package com.exasol.adapter.dialects.elasticsearch;

/**
 * Map Exasol column names to ElasticSearchSQL column names and vice versa.
 *
 * NOTE: This is necessary because ElasticSearch nested column names are case sensitive dot-separated names, as in
 * `book.author.name`. We cannot use this identifier in the Exasol database when performing queries, as the Exasol SQL
 * dialect doesn't allow dots(.) in quoted identifiers.
 */
public class ColumnNameMapper {
    private static final String EXASOL_COLUMN_NAME_PUNCTUATION_CHAR = "/";
    private static final String ES_COLUMN_NAME_PUNCTUATION_CHAR = ".";

    /**
     * Map the passed columnName to an Exasol column name.
     *
     * @param columnName column name to be mapped
     * @returnan Exasol representation of the passed columnName
     */
    public static String mapToExasolDialect(final String columnName) {
        return columnName.replace(ES_COLUMN_NAME_PUNCTUATION_CHAR, EXASOL_COLUMN_NAME_PUNCTUATION_CHAR);
    }

    /**
     * Map the passed columnName to an ElasticSearchSQL column name.
     *
     * @param columnName column name to be mapped
     * @return ElasticSearchSQL representation of the passed columnName
     */
    public static String mapToESDialect(final String columnName) {
        return columnName.replace(EXASOL_COLUMN_NAME_PUNCTUATION_CHAR, ES_COLUMN_NAME_PUNCTUATION_CHAR);
    }
}
