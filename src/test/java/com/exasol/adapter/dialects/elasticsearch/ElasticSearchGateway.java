package com.exasol.adapter.dialects.elasticsearch;

import java.io.*;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jsonb.JsonbJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

/**
 * Gateway for interacting with ElasticSearch data source.
 */
public class ElasticSearchGateway {
    private final ElasticsearchClient client;

    private ElasticSearchGateway(final ElasticsearchClient client) {
        this.client = client;
    }

    /**
     * Connect to an ElasticSearch data source in the given httpHostAddress.
     *
     * @param httpHostAddress a string containing host and port
     */
    public static ElasticSearchGateway connectTo(final String httpHostAddress) {
        final RestClient restClient = RestClient.builder(HttpHost.create(httpHostAddress)).build();
        final ElasticsearchTransport transport = new RestClientTransport(restClient, new JsonbJsonpMapper());
        final ElasticsearchClient client = new ElasticsearchClient(transport);
        return new ElasticSearchGateway(client);
    }

    /**
     * Index a new document in Json format in the data source.
     *
     * @param indexName
     * @param jsonSource
     * @throws IOException
     */
    public void indexDocument(final String indexName, final String jsonSource) {
        try {
            client.index(i -> i.index(indexName).withJson(new StringReader(jsonSource)));
        } catch (final IOException exception) {
            throw new UncheckedIOException("Failed to index document", exception);
        }
    }

    /**
     * Create an index in the data source with the given name.
     *
     * @param indexName
     * @throws IOException
     */
    public void createIndex(final String indexName) {
        try {
            client.indices().create(i -> i.index(indexName));
        } catch (final IOException exception) {
            throw new UncheckedIOException("Failed to create index", exception);
        }
    }

    /**
     * Drop an index in the data source that matches the given name.
     *
     * @param indexName
     * @throws IOException
     */
    public void dropIndex(final String indexName) {
        try {
            client.indices().delete(i -> i.index(indexName));
        } catch (final IOException exception) {
            throw new UncheckedIOException("Failed to drop index", exception);
        }
    }

    /**
     * Start the trial license, required for performing JDBC operations on the data source.
     */
    public void startTrial() {
        try {
            client.license().postStartTrial();
        } catch (final IOException exception) {
            throw new UncheckedIOException("Failed to start trial", exception);
        }
    }

    /**
     * Close the connection to the data source.
     */
    public void closeConnection() {
        try {
            client._transport().close();
        } catch (final IOException exception) {
            throw new UncheckedIOException("Failed to close Elasticsearch client", exception);
        }
    }
}