package com.exasol.adapter.dialects.elasticsearch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exasol.adapter.AdapterProperties;

class ElasticSearchSqlDialectFactoryTest {
    private ElasticSearchSqlDialectFactory factory;

    @BeforeEach
    void beforeEach() {
        this.factory = new ElasticSearchSqlDialectFactory();
    }

    @Test
    void testGetName() {
        assertThat(this.factory.getSqlDialectName(), equalTo("ES"));
    }

    @Test
    void testCreateDialect() {
        assertThat(this.factory.createSqlDialect(null, AdapterProperties.emptyProperties()),
                instanceOf(ElasticSearchSqlDialect.class));
    }
}
