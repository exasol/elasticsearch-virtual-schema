package com.exasol.adapter.dialects.elasticsearch;

import java.sql.Connection;

import com.exasol.ExaMetadata;
import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.dialects.IdentifierConverter;
import com.exasol.adapter.jdbc.*;

/**
 * Metadata reader implementation for reading metadata from an ElasticSearch remote database.
 */
public class ElasticSearchMetadataReader extends AbstractRemoteMetadataReader {

    /**
     * Creates a new instance of {@link ElasticSearchMetadataReader}.
     *
     * @param connection  JDBC connection to the remote data source
     * @param properties  user defined properties
     * @param exaMetadata metadata of the Exasol database
     */
    public ElasticSearchMetadataReader(final Connection connection, final AdapterProperties properties, final ExaMetadata exaMetadata) {
        super(connection, properties, exaMetadata);
    }

    @Override
    protected ColumnMetadataReader createColumnMetadataReader() {
        return new BaseColumnMetadataReader(this.connection, this.properties, this.exaMetadata, this.identifierConverter);
    }

    @Override
    protected TableMetadataReader createTableMetadataReader() {
        return new BaseTableMetadataReader(this.connection, this.columnMetadataReader, this.properties, this.exaMetadata, this.identifierConverter);
    }

    @Override
    protected IdentifierConverter createIdentifierConverter() {
        return new ElasticSearchIdentifierConverter();
    }
}
