package com.exasol.adapter.dialects.elasticsearch;

import com.exasol.adapter.dialects.*;

/**
 * Map Exasol identifiers to ElasticSearchSQL identifiers and vice versa.
 *
 * NOTE: This is necessary because ElasticSearch nested column names are case-sensitive dot-separated names, as in
 * `book.author.name`. We cannot use this identifier in the Exasol database when performing queries, as the Exasol SQL
 * dialect doesn't allow dots(.) in quoted identifiers.
 */
public class ElasticSearchIdentifierConverter implements IdentifierConverter {
    private static final String EXASOL_COLUMN_NAME_PUNCTUATION_CHAR = "/";
    private static final String ELASTICSEARCH_COLUMN_NAME_PUNCTUATION_CHAR = ".";
    private final IdentifierConverter baseIdentifierConverter = new BaseIdentifierConverter(
            IdentifierCaseHandling.INTERPRET_CASE_SENSITIVE, IdentifierCaseHandling.INTERPRET_CASE_SENSITIVE);

    @Override
    public String convert(final String identifier) {
        return convertToExasolDialect(this.baseIdentifierConverter.convert(identifier));
    }

    @Override
    public IdentifierCaseHandling getUnquotedIdentifierHandling() {
        return this.baseIdentifierConverter.getUnquotedIdentifierHandling();
    }

    @Override
    public IdentifierCaseHandling getQuotedIdentifierHandling() {
        return this.baseIdentifierConverter.getQuotedIdentifierHandling();
    }

    /**
     * Map the passed identifier to an Exasol identifier.
     *
     * @param identifier identifier to be mapped
     * @return Exasol representation of the passed identifier
     */
    public static String convertToExasolDialect(final String identifier) {
        return identifier.replace(ELASTICSEARCH_COLUMN_NAME_PUNCTUATION_CHAR, EXASOL_COLUMN_NAME_PUNCTUATION_CHAR);
    }

    /**
     * Map the passed identifier to an ElasticSearchSQL identifier.
     *
     * @param identifier identifier to be mapped
     * @return ElasticSearchSQL representation of the passed identifier
     */
    public static String convertToElasticSearchDialect(final String identifier) {
        return identifier.replace(EXASOL_COLUMN_NAME_PUNCTUATION_CHAR, ELASTICSEARCH_COLUMN_NAME_PUNCTUATION_CHAR);
    }
}
