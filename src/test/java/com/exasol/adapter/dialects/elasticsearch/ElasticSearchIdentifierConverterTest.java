package com.exasol.adapter.dialects.elasticsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElasticSearchIdentifierConverterTest {
    /**
     * @return A stream of arguments with the ElasticSearch identifier representation on the left, and Exasol's on the
     *         right.
     */
    static Stream<Arguments> mappedIdentifiers() {
        return Stream.of(//
                Arguments.of("book", "book"), //
                Arguments.of("book.author", "book/author"), //
                Arguments.of("book.author.name", "book/author/name"), //
                Arguments.of("book..author", "book//author"), //
                Arguments.of("book.", "book/"), //
                Arguments.of("book..", "book//"), //
                Arguments.of(".book", "/book"), //
                Arguments.of(".", "/"), //
                Arguments.of("", "") //
        );
    }

    @ParameterizedTest
    @MethodSource("mappedIdentifiers")
    void testMapToExasol(final String elasticSearchIdentifier, final String exasolIdentifier) {
        assertEquals(ElasticSearchIdentifierConverter.convertToExasolDialect(elasticSearchIdentifier),
                exasolIdentifier);
    }

    @ParameterizedTest
    @MethodSource("mappedIdentifiers")
    void testMapToElasticSearch(final String elasticSearchIdentifier, final String exasolIdentifier) {
        assertEquals(ElasticSearchIdentifierConverter.convertToElasticSearchDialect(exasolIdentifier),
                elasticSearchIdentifier);
    }
}
