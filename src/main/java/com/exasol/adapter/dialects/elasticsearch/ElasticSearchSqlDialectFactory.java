package com.exasol.adapter.dialects.elasticsearch;

import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.dialects.SqlDialect;
import com.exasol.adapter.dialects.SqlDialectFactory;
import com.exasol.adapter.jdbc.ConnectionFactory;

public class ElasticSearchSqlDialectFactory implements SqlDialectFactory {

    @Override
    public SqlDialect createSqlDialect(final ConnectionFactory connectionFactory, final AdapterProperties properties) {
        return new ElasticSearchSqlDialect(connectionFactory, properties);
    }

    @Override
    public String getSqlDialectName() {
        return ElasticSearchSqlDialect.NAME;
    }

    @Override
    public String getSqlDialectVersion() {
        return null;
    }

}
