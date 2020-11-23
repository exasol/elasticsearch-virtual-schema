package com.exasol.adapter.dialects.elasticsearch;

import java.sql.Connection;

import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.dialects.*;
import com.exasol.adapter.jdbc.*;

/**
 * Metadata reader implementation for reading metadata from an ElasticSearch remote database.
 */
public class ElasticSearchMetadataReader extends AbstractRemoteMetadataReader {

    /**
     * Creates a new instance of {@link ElasticSearchMetadataReader}.
     *
     * @param connection JDBC connection to the remote data source
     * @param properties user defined properties
     */
    public ElasticSearchMetadataReader(final Connection connection, final AdapterProperties properties) {
        super(connection, properties);
    }

    @Override
    protected ColumnMetadataReader createColumnMetadataReader() {
        return new ElasticSearchColumnMetadataReader(this.connection, this.properties, this.identifierConverter);
    }

    @Override
    protected TableMetadataReader createTableMetadataReader() {
        return new BaseTableMetadataReader(this.connection, this.columnMetadataReader, this.properties,
                this.identifierConverter);
    }

    @Override
    protected IdentifierConverter createIdentifierConverter() {
        return new BaseIdentifierConverter(IdentifierCaseHandling.INTERPRET_CASE_SENSITIVE,
                IdentifierCaseHandling.INTERPRET_CASE_SENSITIVE);
    }

}
