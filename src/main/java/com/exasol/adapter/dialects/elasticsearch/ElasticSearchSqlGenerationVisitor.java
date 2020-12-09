package com.exasol.adapter.dialects.elasticsearch;

import com.exasol.adapter.AdapterException;
import com.exasol.adapter.dialects.*;
import com.exasol.adapter.sql.SqlColumn;

/**
 * Implementation of {@link SqlGenerationVisitor} for generating ElasticSearchSQL queries.
 */
public class ElasticSearchSqlGenerationVisitor extends SqlGenerationVisitor {
    /**
     * Construct a new instance of {@link ElasticSearchSqlGenerationVisitor}.
     *
     * @param dialect SQL dialect
     * @param context SQL generation context
     */
    public ElasticSearchSqlGenerationVisitor(final SqlDialect dialect, final SqlGenerationContext context) {
        super(dialect, context);
    }

    @Override
    public String visit(final SqlColumn column) throws AdapterException {
        final String tablePrefix = this.getTablePrefix(column);
        if (!tablePrefix.isBlank()) {
            return tablePrefix + this.getDialect().getTableCatalogAndSchemaSeparator() + this.getColumnName(column);
        }
        else
        {
            return this.getColumnName(column);
        }
    }

    private String getTablePrefix(final SqlColumn column) {
        if (column.hasTableAlias()) {
            return this.getDialect().applyQuote(column.getTableAlias());
        }
        if (this.hasTableName(column)) {
            return this.getDialect().applyQuote(column.getTableName());
        }
        return "";
    }

    private boolean hasTableName(final SqlColumn column) {
        return (column.getTableName() != null) && !column.getTableName().isEmpty();
    }

    private String getColumnName(final SqlColumn column) {
        final String columnName = ColumnNameMapper.mapToESDialect(column.getName());
        return this.getDialect().applyQuote(columnName);
    }
}
