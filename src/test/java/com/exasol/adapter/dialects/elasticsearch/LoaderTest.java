package com.exasol.adapter.dialects.elasticsearch;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class LoaderTest {

    @Test
    void testLoad() throws IOException {
//        final ElasticSearchGateway esGateway = new ElasticSearchGateway("localhost:9200");
//
//        final Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("title", "fun_book");
//        jsonMap.put("author.firstname", "marcelo");
//        jsonMap.put("author.lastname", "ch");
//        jsonMap.put("pages", 14);
//
//        esGateway.indexDocument("book", jsonMap);
        final String m = "abc.de.sfads";
        final String mR = m.replace('.', '/');

        final String m2 = "abcsfads";
        final String m2R = m2.replace('.', '/');
    }
}
