package com.exasol.adapter.dialects.elasticsearch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exasol.adapter.AdapterException;
import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.dialects.SqlDialect;
import com.exasol.adapter.dialects.SqlGenerationContext;
import com.exasol.adapter.jdbc.ConnectionFactory;
import com.exasol.adapter.metadata.ColumnMetadata;
import com.exasol.adapter.metadata.DataType;
import com.exasol.adapter.sql.SqlColumn;

@ExtendWith(MockitoExtension.class)
class ElasticSearchSqlGenerationVisitorTest {
    private ElasticSearchSqlGenerationVisitor elasticSearchSqlGenerationVisitor;

    @BeforeEach
    void beforeEach(@Mock final ConnectionFactory connectionFactoryMock) {
        final SqlDialect dialect = new ElasticSearchSqlDialectFactory().createSqlDialect(connectionFactoryMock,
                AdapterProperties.emptyProperties());
        final SqlGenerationContext context = new SqlGenerationContext(null, null, false);
        this.elasticSearchSqlGenerationVisitor = new ElasticSearchSqlGenerationVisitor(dialect, context);
    }

    @Test
    void testGenerateSqlForColumnWithNoTablePrefix() throws AdapterException {
        final SqlColumn column = new SqlColumnBuilder("test_column").build();
        assertThat(this.elasticSearchSqlGenerationVisitor.visit(column), equalTo("\"test_column\""));
    }

    @Test
    void testGenerateSqlForColumnWithNoTablePrefixAndNestedFields() throws AdapterException {
        final SqlColumn column = new SqlColumnBuilder("test_column/nested_field_1/nested_field_2").build();
        assertThat(this.elasticSearchSqlGenerationVisitor.visit(column),
                equalTo("\"test_column.nested_field_1.nested_field_2\""));
    }

    @Test
    void testGenerateSqlForColumnWithTableNameAsPrefix() throws AdapterException {
        final SqlColumn column = new SqlColumnBuilder("test_column").tableName("table_name").build();
        assertThat(this.elasticSearchSqlGenerationVisitor.visit(column), equalTo("\"table_name\".\"test_column\""));
    }

    @Test
    void testGenerateSqlForColumnWithTableAliasAsPrefix() throws AdapterException {
        final SqlColumn column = new SqlColumnBuilder("test_column").tableAlias("table_alias").build();
        assertThat(this.elasticSearchSqlGenerationVisitor.visit(column), equalTo("\"table_alias\".\"test_column\""));
    }

    private static class SqlColumnBuilder {
        private final String columnName;
        private String tableName;
        private String tableAlias;

        private SqlColumnBuilder(final String columnName) {
            super();
            this.columnName = columnName;
        }

        private SqlColumnBuilder tableName(final String tableName) {
            this.tableName = tableName;
            return this;
        }

        private SqlColumnBuilder tableAlias(final String tableAlias) {
            this.tableAlias = tableAlias;
            return this;
        }

        private SqlColumn build() {
            final ColumnMetadata columnMetadata = ColumnMetadata.builder().name(this.columnName)
                    .type(DataType.createBool()).build();
            return new SqlColumn(1, columnMetadata, this.tableName, this.tableAlias);
        }
    }
}
