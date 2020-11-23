package com.exasol.adapter.dialects.elasticsearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.*;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.license.StartTrialRequest;
import org.elasticsearch.common.xcontent.XContentType;

public class ElasticSearchGateway {

    private final RestHighLevelClient client;

    public ElasticSearchGateway(final String httpHostAddress) {
        this.client = new RestHighLevelClient(RestClient.builder(HttpHost.create(httpHostAddress)));
    }

    public void indexDocumentsFromResource(final String index, final String resourceName) throws IOException {
        final List<String> sources = this.parseResourceToJsonDocuments(resourceName);
        final BulkRequest bulkRequest = this.createBulkRequest(index, sources);
        this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    private List<String> parseResourceToJsonDocuments(final String resourceName) {
        try (final JsonReader jsonReader = Json.createReader(getResourceAsStream(resourceName))) {
            return this.toJsonSources(jsonReader.readArray());
        }
    }

    private InputStream getResourceAsStream(final String resourceName) {
        return ElasticSearchGateway.class.getClassLoader().getResourceAsStream(resourceName);
    }

    private List<String> toJsonSources(final JsonArray jsonArray) {
        return jsonArray.stream().map(JsonValue::toString).collect(Collectors.toList());
    }

    private BulkRequest createBulkRequest(final String index, final List<String> documents) {
        final BulkRequest bulkRequest = new BulkRequest();
        for (final String document : documents) {
            bulkRequest.add(new IndexRequest(index).source(document, XContentType.JSON));
        }
        return bulkRequest;
    }

    public void closeConnection() {
        try {
            this.client.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void indexDocument(final String indexName, final Map<String, Object> jsonMap) throws IOException {
        final IndexRequest indexRequest = new IndexRequest(indexName).source(jsonMap);
        this.client.index(indexRequest, RequestOptions.DEFAULT);
    }

    public void createIndex(final String indexName) {
        // TODO Auto-generated method stub

    }

    public void startTrial() throws IOException {
        this.client.license().startTrial(new StartTrialRequest(true), RequestOptions.DEFAULT);
    }
}