package com.exasol.adapter.dialects.elasticsearch;

import java.io.*;

import javax.net.ssl.*;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

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
     * Create a connection to the given container.
     * 
     * @param container the container to connect to
     * @return a new {@link ElasticSearchGateway}
     */
    public static ElasticSearchGateway connect(final ElasticsearchContainer container) {
        if (container.caCertAsBytes().isPresent()) {
            return ElasticSearchGateway.connectViaTls("https://" + container.getHttpHostAddress(),
                    container.createSslContextFromCa(), "elastic",
                    ElasticsearchContainer.ELASTICSEARCH_DEFAULT_PASSWORD);
        } else {
            return ElasticSearchGateway.connect("http://" + container.getHttpHostAddress(), "elastic",
                    ElasticsearchContainer.ELASTICSEARCH_DEFAULT_PASSWORD);
        }
    }

    private static ElasticSearchGateway connectViaTls(final String httpHostAddress, final SSLContext sslcontext,
            final String username, final String password) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        final HttpHost host = HttpHost.create(httpHostAddress);
        final HostnameVerifier hostnameVerifier = new TrustSpecificHostnameVerifier(host.getHostName());
        final RestClient restClient = RestClient.builder(host)
                .setHttpClientConfigCallback(
                        httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLHostnameVerifier(hostnameVerifier).setSSLContext(sslcontext))
                .build();
        final ElasticsearchTransport transport = new RestClientTransport(restClient, new JsonbJsonpMapper());
        final ElasticsearchClient client = new ElasticsearchClient(transport);
        return new ElasticSearchGateway(client);
    }

    private static ElasticSearchGateway connect(final String httpHostAddress, final String username,
            final String password) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        final HttpHost host = HttpHost.create(httpHostAddress);
        final RestClient restClient = RestClient.builder(host).build();
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
            client.license().postStartTrial(t -> t.acknowledge(true));
        } catch (final IOException exception) {
            throw new UncheckedIOException("Failed to start trial: " + exception.getMessage(), exception);
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

    private static class TrustSpecificHostnameVerifier implements HostnameVerifier {
        private final String expectedHostname;

        public TrustSpecificHostnameVerifier(final String expectedHostname) {
            this.expectedHostname = expectedHostname;
        }

        @Override
        public boolean verify(final String hostname, final SSLSession session) {
            return this.expectedHostname.equals(hostname);
        }
    }
}