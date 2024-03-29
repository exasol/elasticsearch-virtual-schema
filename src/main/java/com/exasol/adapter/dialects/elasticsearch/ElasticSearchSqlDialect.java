package com.exasol.adapter.dialects.elasticsearch;

import static com.exasol.adapter.capabilities.AggregateFunctionCapability.*;
import static com.exasol.adapter.capabilities.LiteralCapability.*;
import static com.exasol.adapter.capabilities.MainCapability.*;
import static com.exasol.adapter.capabilities.PredicateCapability.*;
import static com.exasol.adapter.capabilities.ScalarFunctionCapability.*;

import java.sql.SQLException;
import java.util.*;

import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.capabilities.Capabilities;
import com.exasol.adapter.dialects.*;
import com.exasol.adapter.dialects.rewriting.ImportIntoTemporaryTableQueryRewriter;
import com.exasol.adapter.dialects.rewriting.SqlGenerationContext;
import com.exasol.adapter.jdbc.*;
import com.exasol.adapter.sql.ScalarFunction;
import com.exasol.errorreporting.ExaError;

/**
 * Implementation of SQL dialect for ElasticSearch.
 */
public class ElasticSearchSqlDialect extends AbstractSqlDialect {
    static final String NAME = "ES";
    private static final Capabilities CAPABILITIES = createCapabilityList();

    private static Capabilities createCapabilityList() {
        return Capabilities //
                .builder() //
                .addMain(SELECTLIST_PROJECTION, SELECTLIST_EXPRESSIONS, FILTER_EXPRESSIONS, AGGREGATE_SINGLE_GROUP,
                        AGGREGATE_GROUP_BY_COLUMN, AGGREGATE_GROUP_BY_TUPLE, AGGREGATE_HAVING, ORDER_BY_COLUMN,
                        ORDER_BY_EXPRESSION, LIMIT) //
                .addPredicate(AND, OR, NOT, EQUAL, NOTEQUAL, LESS, LESSEQUAL, BETWEEN, IN_CONSTLIST, IS_NULL,
                        IS_NOT_NULL, LIKE) //
                .addLiteral(BOOL, DOUBLE, EXACTNUMERIC, STRING) //
                .addAggregateFunction(COUNT, COUNT_STAR, COUNT_DISTINCT, SUM, MIN, MAX, AVG, FIRST_VALUE, LAST_VALUE,
                        STDDEV_POP, STDDEV_SAMP, VAR_POP, VAR_SAMP) //
                .addScalarFunction(ADD, SUB, MULT, NEG, ABS, ACOS, ASIN, ATAN, ATAN2, CEIL, COS, COSH, COT, DEGREES,
                        DIV, EXP, FLOOR, GREATEST, LEAST, LN, CHR, MOD, POWER, RADIANS, RAND, ROUND, SIGN, SIN, SINH,
                        SQRT, TAN, TRUNC, ASCII, BIT_LENGTH, CONCAT, INSERT, LENGTH, OCTET_LENGTH, REPEAT, REPLACE,
                        RIGHT, SPACE, CURRENT_DATE, CURRENT_TIMESTAMP, DATE_TRUNC, DAY, EXTRACT, HOUR, MINUTE, MONTH,
                        WEEK, YEAR, ST_X, ST_Y, CAST, CASE) //
                .build();
    }

    /**
     * Creates a new instance of {@link ElasticSearchSqlDialect}.
     *
     * @param connectionFactory factory for JDBC connection to remote data source
     * @param properties        user defined properties
     */
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
    public Map<ScalarFunction, String> getScalarFunctionAliases() {
        final Map<ScalarFunction, String> scalarFunctionAliases = new EnumMap<>(ScalarFunction.class);
        scalarFunctionAliases.put(ScalarFunction.LN, "LOG");
        scalarFunctionAliases.put(ScalarFunction.CHR, "CHAR");
        return scalarFunctionAliases;
    }

    @Override
    public Map<ScalarFunction, String> getBinaryInfixFunctionAliases() {
        final Map<ScalarFunction, String> aliases = new EnumMap<>(ScalarFunction.class);
        aliases.put(ScalarFunction.ADD, "+");
        aliases.put(ScalarFunction.SUB, "-");
        aliases.put(ScalarFunction.MULT, "*");
        aliases.put(ScalarFunction.DIV, "/");
        return aliases;
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
            throw new RemoteMetadataReaderException(ExaError.messageBuilder("E-VSES-1")
                    .message("Unable to create ElasticSearch remote metadata reader.").toString(), exception);
        }
    }

    @Override
    protected QueryRewriter createQueryRewriter() {
        return new ImportIntoTemporaryTableQueryRewriter(this, createRemoteMetadataReader(), this.connectionFactory);
    }

    @Override
    public SqlGenerator getSqlGenerator(final SqlGenerationContext context) {
        return new ElasticSearchSqlGenerationVisitor(this, context);
    }

    @Override
    public String getStringLiteral(final String value) {
        return this.quoteLiteralStringWithSingleQuote(value);
    }
}