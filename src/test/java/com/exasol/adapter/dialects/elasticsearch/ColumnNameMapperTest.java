package com.exasol.adapter.dialects.elasticsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ColumnNameMapperTest {
    /**
     * @return A stream of arguments with the ElasticSearch column name representation on the left, and Exasol's on the
     *         right.
     */
    static Stream<Arguments> mappedColumnNames() {
        return Stream.of(//
                Arguments.of("book", "book"), //
                Arguments.of("book.author", "book/author"), //
                Arguments.of("book.author.name", "book/author/name") //
        );
    }

    @ParameterizedTest
    @MethodSource("mappedColumnNames")
    void testMapToExasol(final String elasticSearchColumnName, final String exasolColumnName) {
        assertEquals(ColumnNameMapper.mapToExasolDialect(elasticSearchColumnName), exasolColumnName);
    }

    @ParameterizedTest
    @MethodSource("mappedColumnNames")
    void testMapToElasticSearch(final String elasticSearchColumnName, final String exasolColumnName) {
        assertEquals(ColumnNameMapper.mapToESDialect(exasolColumnName), elasticSearchColumnName);
    }
}
