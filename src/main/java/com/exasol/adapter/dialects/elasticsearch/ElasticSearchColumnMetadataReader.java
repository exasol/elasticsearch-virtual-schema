package com.exasol.adapter.dialects.elasticsearch;

import java.sql.Connection;

import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.dialects.IdentifierConverter;
import com.exasol.adapter.jdbc.BaseColumnMetadataReader;

/**
 * This class implements ElasticSearch-specific reading of column metadata.
 */
public class ElasticSearchColumnMetadataReader extends BaseColumnMetadataReader {
    /**
     * Create a new instance of the {@link ElasticSearchColumnMetadataReader}.
     *
     * @param connection          JDBC connection through which the column metadata is read from the remote database
     * @param properties          user-defined adapter properties
     * @param identifierConverter converter between ElasticSearch and Exasol identifiers
     */
    public ElasticSearchColumnMetadataReader(final Connection connection, final AdapterProperties properties,
            final IdentifierConverter identifierConverter) {
        super(connection, properties, identifierConverter);
    }

    @Override
    protected String mapColumnName(final String columnName) {
        return ColumnNameMapper.mapToExasolDialect(columnName);
    }

}
