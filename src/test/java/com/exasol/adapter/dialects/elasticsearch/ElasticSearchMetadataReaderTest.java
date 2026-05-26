package com.exasol.adapter.dialects.elasticsearch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exasol.ExaMetadata;
import com.exasol.adapter.AdapterProperties;
import com.exasol.adapter.dialects.IdentifierCaseHandling;
import com.exasol.adapter.dialects.IdentifierConverter;
import com.exasol.adapter.jdbc.BaseColumnMetadataReader;
import com.exasol.adapter.jdbc.BaseTableMetadataReader;

@ExtendWith(MockitoExtension.class)
class ElasticSearchMetadataReaderTest {
    private ElasticSearchMetadataReader reader;
    @Mock
    ExaMetadata exaMetadataMock;

    @BeforeEach
    void beforeEach() {
        this.reader = new ElasticSearchMetadataReader(null, AdapterProperties.emptyProperties(), exaMetadataMock);
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
