package com.gbr.nyan.feature;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.web.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CanCreateSubscription {
    @LocalServerPort
    private int serverPort;

    @Test
    public void returnsOk() throws Exception {
        // TODO: add oauth authorization header, i.e.
        // authorization: OAuth oauth_consumer_key="a-cat-named-nyan-105612", oauth_nonce="-7162822245644540921", oauth_signature="0kL0WN17Sw8yhaAw4PkkFvVEnbY%3D", oauth_signature_method="HMAC-SHA1", oauth_timestamp="1461183771", oauth_version="1.0"

        HttpGet createSubscription = new HttpGet(baseServerUrl() + "/subscription/create/notification");
        CloseableHttpResponse response = appDirectHttpClient().execute(createSubscription);

        assertThat(response.getStatusLine().getStatusCode(), is(200));
        assertThat(EntityUtils.toString(response.getEntity()), is("{\"status\":\"success\"}"));
    }

    private CloseableHttpClient appDirectHttpClient() {
        return HttpClients.custom()
                .setUserAgent("Apache-HttpClient/4.3.6 (java 1.5)")
                .setDefaultHeaders(asList(
                        new BasicHeader(HttpHeaders.ACCEPT, "application/json, application/xml"),
                        new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate")))
                .disableRedirectHandling()
                .build();
    }

    private String baseServerUrl() {
        return "http://localhost:" + serverPort;
    }
}
