package com.exasol.adapter.dialects.elasticsearch;

import static com.exasol.adapter.capabilities.AggregateFunctionCapability.*;
import static com.exasol.adapter.capabilities.LiteralCapability.*;
import static com.exasol.adapter.capabilities.MainCapability.*;
import static com.exasol.adapter.capabilities.PredicateCapability.*;

import java.sql.SQLException;
import java.util.Set;

import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.capabilities.Capabilities;
import com.exasol.adapter.dialects.*;
import com.exasol.adapter.jdbc.*;

/**
 * This class implements the PostgreSQL dialect.
 */
public class ElasticSearchSqlDialect extends AbstractSqlDialect {

    static final String NAME = "ES";
    private static final Capabilities CAPABILITIES = createCapabilityList();

    private static Capabilities createCapabilityList() {
        return Capabilities //
                .builder() //
                .addMain(SELECTLIST_PROJECTION, SELECTLIST_EXPRESSIONS, FILTER_EXPRESSIONS, AGGREGATE_SINGLE_GROUP,
                        AGGREGATE_GROUP_BY_COLUMN, AGGREGATE_GROUP_BY_EXPRESSION, AGGREGATE_GROUP_BY_TUPLE,
                        AGGREGATE_HAVING, ORDER_BY_COLUMN, ORDER_BY_EXPRESSION, LIMIT) //
                .addPredicate(AND, OR, NOT, NOTEQUAL, LESS, LESSEQUAL, BETWEEN, IN_CONSTLIST, IS_NULL, IS_NOT_NULL,
                        LIKE) //
                .addLiteral(NULL, BOOL, DOUBLE, EXACTNUMERIC, STRING, INTERVAL) //
                .addAggregateFunction(COUNT, COUNT_STAR, COUNT_DISTINCT, SUM, SUM_DISTINCT, MIN, MAX, AVG, AVG_DISTINCT,
                        FIRST_VALUE, LAST_VALUE, STDDEV_POP, STDDEV_POP_DISTINCT, STDDEV_SAMP, STDDEV_SAMP_DISTINCT,
                        VAR_POP, VAR_POP_DISTINCT, VAR_SAMP, VAR_SAMP_DISTINCT)//
                .build();
    }

    public ElasticSearchSqlDialect(final ConnectionFactory connectionFactory, final AdapterProperties properties) {
        super(connectionFactory, properties, Set.of());
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Capabilities getCapabilities() {
        return CAPABILITIES;
    }

    @Override
    public StructureElementSupport supportsJdbcCatalogs() {
        return StructureElementSupport.NONE;
    }

    @Override
    public StructureElementSupport supportsJdbcSchemas() {
        return StructureElementSupport.NONE;
    }

    @Override
    public String applyQuote(final String identifier) {
        return "\"" + identifier + "\"";
    }

    @Override
    public boolean requiresCatalogQualifiedTableNames(final SqlGenerationContext context) {
        return false;
    }

    @Override
    public boolean requiresSchemaQualifiedTableNames(final SqlGenerationContext context) {
        return false;
    }

    @Override
    public NullSorting getDefaultNullSorting() {
        return NullSorting.NULLS_SORTED_HIGH;
    }

    @Override
    protected RemoteMetadataReader createRemoteMetadataReader() {
        try {
            return new ElasticSearchMetadataReader(this.connectionFactory.getConnection(), this.properties);
        } catch (final SQLException exception) {
            throw new RemoteMetadataReaderException("Unable to create ElasticSearch remote metadata reader.",
                    exception);
        }
    }

    @Override
    protected QueryRewriter createQueryRewriter() {
        return new BaseQueryRewriter(this, createRemoteMetadataReader(), this.connectionFactory);
    }
}