package com.exasol.adapter.dialects.elasticsearch;

import static com.exasol.adapter.capabilities.AggregateFunctionCapability.*;
import static com.exasol.adapter.capabilities.LiteralCapability.*;
import static com.exasol.adapter.capabilities.MainCapability.*;
import static com.exasol.adapter.capabilities.PredicateCapability.*;
import static com.exasol.adapter.capabilities.ScalarFunctionCapability.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.capabilities.Capabilities;
import com.exasol.adapter.dialects.ImportFromJDBCQueryRewriter;
import com.exasol.adapter.dialects.SqlDialect.NullSorting;
import com.exasol.adapter.dialects.SqlDialect.StructureElementSupport;
import com.exasol.adapter.jdbc.ConnectionFactory;
import com.exasol.adapter.jdbc.RemoteMetadataReaderException;

@ExtendWith(MockitoExtension.class)
class ElasticSearchSqlDialectTest {
    private ElasticSearchSqlDialect dialect;
    @Mock
    private ConnectionFactory connectionFactoryMock;

    @BeforeEach
    void beforeEach() {
        this.dialect = new ElasticSearchSqlDialect(this.connectionFactoryMock, AdapterProperties.emptyProperties());
    }

    @Test
    void testExasolSqlDialectSupportsAllCapabilities() {
        final Capabilities capabilities = this.dialect.getCapabilities();
        assertAll(
                () -> assertThat(capabilities.getMainCapabilities(),
                        containsInAnyOrder(SELECTLIST_PROJECTION, SELECTLIST_EXPRESSIONS, FILTER_EXPRESSIONS,
                                AGGREGATE_SINGLE_GROUP, AGGREGATE_GROUP_BY_COLUMN, AGGREGATE_GROUP_BY_TUPLE,
                                AGGREGATE_HAVING, ORDER_BY_COLUMN, ORDER_BY_EXPRESSION, LIMIT)),
                () -> assertThat(capabilities.getPredicateCapabilities(),
                        containsInAnyOrder(AND, OR, NOT, NOTEQUAL, LESS, LESSEQUAL, BETWEEN, IN_CONSTLIST, IS_NULL,
                                IS_NOT_NULL, LIKE)),
                () -> assertThat(capabilities.getLiteralCapabilities(),
                        containsInAnyOrder(NULL, BOOL, DOUBLE, EXACTNUMERIC, STRING, INTERVAL)),
                () -> assertThat(capabilities.getAggregateFunctionCapabilities(),
                        containsInAnyOrder(COUNT, COUNT_STAR, COUNT_DISTINCT, SUM, SUM_DISTINCT, MIN, MAX, AVG,
                                AVG_DISTINCT, FIRST_VALUE, LAST_VALUE, STDDEV_POP, STDDEV_POP_DISTINCT, STDDEV_SAMP,
                                STDDEV_SAMP_DISTINCT, VAR_POP, VAR_POP_DISTINCT, VAR_SAMP, VAR_SAMP_DISTINCT)),
                () -> assertThat(capabilities.getScalarFunctionCapabilities(),
                        containsInAnyOrder(ADD, SUB, MULT, FLOAT_DIV, NEG, ABS, ACOS, ASIN, ATAN, ATAN2, CEIL, COS,
                                COSH, COT, DEGREES, EXP, FLOOR, GREATEST, LEAST, LOG, MOD, POWER, RADIANS, RAND, ROUND,
                                SIGN, SIN, SINH, SQRT, TAN, TRUNC, ASCII, BIT_LENGTH, CHR, CONCAT, INSERT, LENGTH,
                                LOCATE, LTRIM, OCTET_LENGTH, REPEAT, REPLACE, RIGHT, RTRIM, SPACE, TRIM, CURRENT_DATE,
                                CURRENT_TIMESTAMP, DATE_TRUNC, DAY, EXTRACT, HOUR, MINUTE, MONTH, SECOND, WEEK, YEAR,
                                ST_X, ST_Y, CAST, CASE)));
    }

    @Test
    void testSupportsJdbcCatalogs() {
        assertThat(this.dialect.supportsJdbcCatalogs(), equalTo(StructureElementSupport.NONE));
    }

    @Test
    void testSupportsJdbcSchemas() {
        assertThat(this.dialect.supportsJdbcSchemas(), equalTo(StructureElementSupport.NONE));
    }

    @Test
    void testRequiresCatalogQualifiedTableNames() {
        assertThat(this.dialect.requiresCatalogQualifiedTableNames(null), equalTo(false));
    }

    @Test
    void testRequiresSchemaQualifiedTableNames() {
        assertThat(this.dialect.requiresSchemaQualifiedTableNames(null), equalTo(false));
    }

    @Test
    void testGetDefaultNullSorting() {
        assertThat(this.dialect.getDefaultNullSorting(), equalTo(NullSorting.NULLS_SORTED_HIGH));
    }

    @Test
    void testApplyQuote() {
        assertThat(this.dialect.applyQuote("tableName"), equalTo("\"tableName\""));
    }

    @Test
    void testCreateRemoteMetadataReader(@Mock final Connection connectionMock) throws SQLException {
        when(this.connectionFactoryMock.getConnection()).thenReturn(connectionMock);
        assertThat(this.dialect.createRemoteMetadataReader(), instanceOf(ElasticSearchMetadataReader.class));
    }

    @Test
    void testCreateRemoteMetadataReaderConnectionFails(@Mock final Connection connectionMock) throws SQLException {
        when(this.connectionFactoryMock.getConnection()).thenThrow(new SQLException());
        final RemoteMetadataReaderException exception = assertThrows(RemoteMetadataReaderException.class,
                this.dialect::createRemoteMetadataReader);
        assertThat(exception.getMessage(), containsString("E-VSES-1"));
    }

    @Test
    void testCreateQueryRewriter(@Mock final Connection connectionMock) throws SQLException {
        when(this.connectionFactoryMock.getConnection()).thenReturn(connectionMock);
        assertThat(this.dialect.createQueryRewriter(), instanceOf(ImportFromJDBCQueryRewriter.class));
    }

    @Test
    void testGetSqlGenerationVisitor() throws SQLException {
        assertThat(this.dialect.getSqlGenerationVisitor(null), instanceOf(ElasticSearchSqlGenerationVisitor.class));
    }

    /**
     * @return A stream of arguments with a literal string on the left, and its valid SQL syntax on the right.
     */
    static Stream<Arguments> getMappedStringLiterals() {
        return Stream.of(//
                Arguments.of(null, "NULL"), //
                Arguments.of("string_literal_no_inner_quotes", "'string_literal_no_inner_quotes'"), //
                Arguments.of("string_literal_with_'inner_quotes'", "'string_literal_with_''inner_quotes'''") //
        );
    }

    @ParameterizedTest
    @MethodSource("getMappedStringLiterals")
    void getStringLiteral(final String value, final String expected) throws SQLException {
        assertThat(this.dialect.getStringLiteral(value), equalTo(expected));
    }
}
