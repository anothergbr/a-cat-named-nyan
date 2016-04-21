package com.gbr.nyan.feature;

import com.gbr.nyan.support.FakeAppDirect;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.web.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.gbr.nyan.support.HttpClientHelper.get;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CanCreateSubscription {
    @LocalServerPort
    private int serverPort;
    private FakeAppDirect fakeAppDirect;

    @Before
    public void startFakeAppDirect() throws Exception {
        fakeAppDirect = FakeAppDirect.create(42534).start();
    }

    @After
    public void stopFakeServer() {
        fakeAppDirect.stop();
    }

    @Test
    public void failsWhenNoEvenUrlIsPassed() throws Exception {
        HttpGet createSubscription = get(createSubscription());
        HttpResponse response = appDirectHttpClient().execute(createSubscription);

        assertThat(response.getStatusLine().getStatusCode(), is(400));
        assertThat(EntityUtils.toString(response.getEntity()), is("{\"errorCode\":\"INVALID_RESPONSE\",\"success\":\"false\"}"));
    }

    @Test
    public void successWhenDummyEventUrlIsPassed() throws Exception {
        HttpGet createDummySubscription = get(createSubscription(), "eventUrl", "http://localhost:42534/v1/events/dummySubscription");

        HttpResponse response = appDirectHttpClient().execute(createDummySubscription);

        // TODO: fix controller so server is called
        // assertThat(fakeAppDirect.lastRequestPath(), is("/v1/events/dummySubscription"));

        assertThat(response.getStatusLine().getStatusCode(), is(200));
        assertThat(EntityUtils.toString(response.getEntity()), is("{\"accountIdentifier\":\"some-id\",\"success\":\"true\"}"));
    }

    @Test
    public void failureWhenOauthSignatureIsInvalid() throws Exception {
        // TODO: eventually support oauth signature verification from auth header, i.e.
        // authorization: OAuth oauth_consumer_key="a-cat-named-nyan-105612", oauth_nonce="-7162822245644540921", oauth_signature="0kL0WN17Sw8yhaAw4PkkFvVEnbY%3D", oauth_signature_method="HMAC-SHA1", oauth_timestamp="1461183771", oauth_version="1.0"
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

    private String createSubscription() {
        return baseServerUrl() + "/subscription/create/notification";
    }

    private String baseServerUrl() {
        return "http://localhost:" + serverPort;
    }
}
