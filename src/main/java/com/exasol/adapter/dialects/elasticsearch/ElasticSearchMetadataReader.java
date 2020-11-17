package com.exasol.adapter.dialects.elasticsearch;

import java.sql.Connection;

import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.dialects.*;
import com.exasol.adapter.jdbc.*;

public class ElasticSearchMetadataReader extends AbstractRemoteMetadataReader {

    public ElasticSearchMetadataReader(final Connection connection, final AdapterProperties properties) {
        super(connection, properties);
    }

    @Override
    protected ColumnMetadataReader createColumnMetadataReader() {
        return new BaseColumnMetadataReader(this.connection, this.properties, this.identifierConverter);
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
