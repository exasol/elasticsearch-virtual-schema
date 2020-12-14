package com.exasol.adapter.dialects.elasticsearch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.Connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.dialects.IdentifierCaseHandling;
import com.exasol.adapter.dialects.IdentifierConverter;
import com.exasol.adapter.jdbc.BaseColumnMetadataReader;
import com.exasol.adapter.jdbc.BaseTableMetadataReader;

class ElasticSearchMetadataReaderTest {
    private ElasticSearchMetadataReader reader;
    @Mock
    private Connection connectionMock;

    @BeforeEach
    void beforeEach() {
        this.reader = new ElasticSearchMetadataReader(this.connectionMock, AdapterProperties.emptyProperties());
    }

    @Test
    void testGetTableMetadataReader() {
        assertThat(this.reader.getTableMetadataReader(), instanceOf(BaseTableMetadataReader.class));
    }

    @Test
    void testGetColumnMetadataReader() {
        assertThat(this.reader.getColumnMetadataReader(), instanceOf(BaseColumnMetadataReader.class));
    }

    @Test
    void testCreateIdentifierConverter() {
        final IdentifierConverter converter = this.reader.getIdentifierConverter();
        assertAll(() -> assertThat(converter, instanceOf(ElasticSearchIdentifierConverter.class)),
                () -> assertThat(converter.getQuotedIdentifierHandling(),
                        equalTo(IdentifierCaseHandling.INTERPRET_CASE_SENSITIVE)),
                () -> assertThat(converter.getUnquotedIdentifierHandling(),
                        equalTo(IdentifierCaseHandling.INTERPRET_CASE_SENSITIVE)));
    }
}
