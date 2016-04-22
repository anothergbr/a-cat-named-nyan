package com.gbr.nyan.web.support;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.net.URISyntaxException;

import static java.util.Collections.singletonList;

/**
 * Small wrapper around Apache's http client (reduces exposed API surface)
 */
public class HttpClient {
    private final CloseableHttpClient apacheClient;

    public static HttpClient aClientAcceptingOnlyJson() {
        CloseableHttpClient apacheClient = HttpClients.custom()
                .setDefaultHeaders(singletonList(new BasicHeader(HttpHeaders.ACCEPT, "application/json")))
                .disableRedirectHandling()
                .build();

        return new HttpClient(apacheClient);
    }

    private HttpClient(CloseableHttpClient apacheClient) {
        this.apacheClient = apacheClient;
    }

    public String get(String url) throws Exception {
        CloseableHttpResponse response = apacheClient.execute(buildGet(url));
        if (response.getStatusLine().getStatusCode() > 299) {
            throw new Exception("Request to " + url + " failed with status " + response.getStatusLine());
        }
        return EntityUtils.toString(response.getEntity());
    }

    private HttpGet buildGet(String endpoint, String... params) throws URISyntaxException {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("pass params in name=value form");
        }

        URIBuilder builder = new URIBuilder(endpoint);
        for (int i = 0; i < params.length; i = i + 2) {
            builder.addParameter(params[i], params[i + 1]).build();
        }
        return new HttpGet(builder.build());
    }
}
