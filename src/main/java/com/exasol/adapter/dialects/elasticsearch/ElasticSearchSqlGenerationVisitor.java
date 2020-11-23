package com.exasol.adapter.dialects.elasticsearch;

import com.exasol.adapter.AdapterException;
import com.exasol.adapter.dialects.*;
import com.exasol.adapter.sql.SqlColumn;

/**
 * Implementation of {@link SqlGenerationVisitor} for generating ElasticSearchSQL queries.
 */
public class ElasticSearchSqlGenerationVisitor extends SqlGenerationVisitor {

    /**
     * Constructs a new instance of {@link ElasticSearchSqlGenerationVisitor}.
     *
     * @param dialect SQL dialect
     * @param context SQL generation context
     */
    public ElasticSearchSqlGenerationVisitor(final SqlDialect dialect, final SqlGenerationContext context) {
        super(dialect, context);
    }

    @Override
    public String visit(final SqlColumn column) throws AdapterException {
        return this.getTablePrefix(column) + this.getColumnName(column);
    }

    private String getTablePrefix(final SqlColumn column) {
        if (column.hasTableAlias()) {
            return this.getTablePrefixWithAlias(column.getTableAlias());
        }
        if (this.hasTableName(column)) {
            return this.getTablePrefixWithName(column.getTableName());
        }
        return "";
    }

    private String getTablePrefixWithAlias(final String tableAlias) {
        return this.getDialect().applyQuote(tableAlias) + this.getDialect().getTableCatalogAndSchemaSeparator();
    }

    private boolean hasTableName(final SqlColumn column) {
        return (column.getTableName() != null) && !column.getTableName().isEmpty();
    }

    private String getTablePrefixWithName(final String tableName) {
        return this.getDialect().applyQuote(tableName) + this.getDialect().getTableCatalogAndSchemaSeparator();
    }

    private String getColumnName(final SqlColumn column) {
        final String columnName = ColumnNameMapper.mapToESDialect(column.getName());
        return this.getDialect().applyQuote(columnName);
    }
}
