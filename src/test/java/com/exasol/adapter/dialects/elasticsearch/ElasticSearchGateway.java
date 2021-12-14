package com.exasol.adapter.dialects.elasticsearch;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.license.StartTrialRequest;
import org.elasticsearch.xcontent.XContentType;

/**
 * Gateway for interacting with ElasticSearch data source.
 */
public class ElasticSearchGateway {
    private final RestHighLevelClient client;

    private ElasticSearchGateway(final String httpHostAddress) {
        this.client = new RestHighLevelClient(RestClient.builder(HttpHost.create(httpHostAddress)));
    }

    /**
     * Connect to an ElasticSearch data source in the given httpHostAddress and the standard port.
     *
     * @param httpHostAddress
     */
    public static ElasticSearchGateway connectTo(final String httpHostAddress) {
        return new ElasticSearchGateway(httpHostAddress);
    }

    /**
     * Index a new document in Json format in the data source.
     *
     * @param indexName
     * @param jsonSource
     * @throws IOException
     */
    public void indexDocument(final String indexName, final String jsonSource) throws IOException {
        final IndexRequest indexRequest = new IndexRequest(indexName).source(jsonSource, XContentType.JSON);
        this.client.index(indexRequest, RequestOptions.DEFAULT);
    }

    /**
     * Create an index in the data source with the given name.
     *
     * @param indexName
     * @throws IOException
     */
    public void createIndex(final String indexName) throws IOException {
        this.client.indices().create(new CreateIndexRequest(indexName), RequestOptions.DEFAULT);
    }

    /**
     * Drop an index in the data source that matches the given name.
     *
     * @param indexName
     * @throws IOException
     */
    public void dropIndex(final String indexName) throws IOException {
        this.client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
    }

    /**
     * Start the trial license, required for performing JDBC operations on the data source.
     *
     * @throws IOException
     */
    public void startTrial() throws IOException {
        this.client.license().startTrial(new StartTrialRequest(true), RequestOptions.DEFAULT);
    }

    /**
     * Close the connection to the data source.
     */
    public void closeConnection() {
        try {
            this.client.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}