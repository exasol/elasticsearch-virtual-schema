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
import com.exasol.adapter.dialects.rewriting.SqlGenerationContext;
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
        generateSqlForColumn("test_column").expectedSql("\"test_column\"").verify();
    }

    @Test
    void testGenerateSqlForColumnWithNoTablePrefixAndNestedFields() throws AdapterException {
        generateSqlForColumn("test_column/nested_field_1/nested_field_2")
                .expectedSql("\"test_column.nested_field_1.nested_field_2\"").verify();
    }

    @Test
    void testGenerateSqlForColumnWithTableNameAsPrefix() throws AdapterException {
        generateSqlForColumn("test_column").tableName("table_name").expectedSql("\"table_name\".\"test_column\"")
                .verify();
    }

    @Test
    void testGenerateSqlForColumnWithTableAliasAsPrefix() throws AdapterException {
        generateSqlForColumn("test_column").tableAlias("table_alias").expectedSql("\"table_alias\".\"test_column\"")
                .verify();
    }

    @Test
    void testGenerateSqlForColumnWithMalformedColumnName() throws AdapterException {
        generateSqlForColumn("/test_column").tableAlias("table_alias").expectedSql("\"table_alias\".\".test_column\"")
                .verify();
    }

    @Test
    void testGenerateSqlForColumnWithMalformedTableAlias() throws AdapterException {
        generateSqlForColumn("test_column").tableAlias("//table_alias").expectedSql("\"//table_alias\".\"test_column\"")
                .verify();
    }

    @Test
    void testGenerateSqlForColumnWithMalformedColumnNameAndTableAlias() throws AdapterException {
        generateSqlForColumn("/").tableAlias("/").expectedSql("\"/\".\".\"").verify();
    }

    private SqlColumnVerifier generateSqlForColumn(final String columnName) {
        return new SqlColumnVerifier(this, columnName);
    }

    private static class SqlColumnVerifier {
        private final String columnName;
        private String tableName;
        private String tableAlias;
        private String expectedSql;
        private final ElasticSearchSqlGenerationVisitorTest parent;

        private SqlColumnVerifier(final ElasticSearchSqlGenerationVisitorTest parent, final String columnName) {
            this.parent = parent;
            this.columnName = columnName;
        }

        private SqlColumnVerifier tableName(final String tableName) {
            this.tableName = tableName;
            return this;
        }

        private SqlColumnVerifier tableAlias(final String tableAlias) {
            this.tableAlias = tableAlias;
            return this;
        }

        private SqlColumnVerifier expectedSql(final String expectedSql) {
            this.expectedSql = expectedSql;
            return this;
        }

        private void verify() throws AdapterException {
            assertThat(this.parent.elasticSearchSqlGenerationVisitor.visit(this.getSqlColumn()),
                    equalTo(this.expectedSql));
        }

        private SqlColumn getSqlColumn() {
            final ColumnMetadata columnMetadata = ColumnMetadata.builder().name(this.columnName)
                    .type(DataType.createBool()).build();
            return new SqlColumn(1, columnMetadata, this.tableName, this.tableAlias);
        }
    }
}
