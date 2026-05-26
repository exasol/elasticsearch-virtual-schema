package com.exasol.adapter.dialects.elasticsearch;

import com.exasol.adapter.dialects.*;
import com.exasol.logging.VersionCollector;

/**
 * Factory for {@link ElasticSearchSqlDialect}.
 */
public class ElasticSearchSqlDialectFactory implements SqlDialectFactory {

    @Override
    public SqlDialect createSqlDialect(final JDBCAdapterContext context) {
        return new ElasticSearchSqlDialect(context);
    }

    @Override
    public String getSqlDialectName() {
        return ElasticSearchSqlDialect.NAME;
    }

    @Override
    public String getSqlDialectVersion() {
        return new VersionCollector("META-INF/maven/com.exasol/elasticsearch-virtual-schema/pom.properties")
                .getVersionNumber();
    }

    @Override
    public String getAdapterProjectShortTag() {
        return "VSES";
    }
}
