package com.exasol.adapter.dialects.elasticsearch;

import static com.exasol.adapter.capabilities.AggregateFunctionCapability.*;
import static com.exasol.adapter.capabilities.LiteralCapability.*;
import static com.exasol.adapter.capabilities.MainCapability.*;
import static com.exasol.adapter.capabilities.PredicateCapability.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.capabilities.Capabilities;
import com.exasol.adapter.dialects.BaseQueryRewriter;
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
        assertAll(() -> assertThat(capabilities.getMainCapabilities(),
                containsInAnyOrder(SELECTLIST_PROJECTION, SELECTLIST_EXPRESSIONS, FILTER_EXPRESSIONS,
                        AGGREGATE_SINGLE_GROUP, AGGREGATE_GROUP_BY_COLUMN, AGGREGATE_GROUP_BY_EXPRESSION,
                        AGGREGATE_GROUP_BY_TUPLE, AGGREGATE_HAVING, ORDER_BY_COLUMN, ORDER_BY_EXPRESSION, LIMIT)),
                () -> assertThat(capabilities.getPredicateCapabilities(),
                        containsInAnyOrder(AND, OR, NOT, NOTEQUAL, LESS, LESSEQUAL, BETWEEN, IN_CONSTLIST, IS_NULL,
                                IS_NOT_NULL, LIKE)),
                () -> assertThat(capabilities.getLiteralCapabilities(),
                        containsInAnyOrder(NULL, BOOL, DOUBLE, EXACTNUMERIC, STRING, INTERVAL)),
                () -> assertThat(capabilities.getAggregateFunctionCapabilities(),
                        containsInAnyOrder(COUNT, COUNT_STAR, COUNT_DISTINCT, SUM, SUM_DISTINCT, MIN, MAX, AVG,
                                AVG_DISTINCT, FIRST_VALUE, LAST_VALUE, STDDEV_POP, STDDEV_POP_DISTINCT, STDDEV_SAMP,
                                STDDEV_SAMP_DISTINCT, VAR_POP, VAR_POP_DISTINCT, VAR_SAMP, VAR_SAMP_DISTINCT)));
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
        assertThat(this.dialect.createQueryRewriter(), instanceOf(BaseQueryRewriter.class));
    }
}
