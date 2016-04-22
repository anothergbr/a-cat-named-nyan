package com.gbr.nyan.feature;

import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import com.gbr.nyan.support.FakeAppDirect;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.web.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.gbr.nyan.support.HttpClientHelper.anAppDirectHttpClient;
import static com.gbr.nyan.support.HttpClientHelper.get;
import static com.gbr.nyan.support.Iterables.toList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CanCreateSubscription {
    @LocalServerPort
    private int serverPort;
    private FakeAppDirect fakeAppDirect;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void startFakeAppDirect() throws Exception {
        fakeAppDirect = FakeAppDirect.create(42534).start();
    }

    @After
    public void stopFakeServer() {
        fakeAppDirect.stop();
    }

    @Test
    public void respondsToStatelessEventsWithWellFormedErrorResponse() throws Exception {
        HttpResponse response = anAppDirectHttpClient().execute(get(createSubscription(), "eventUrl", "http://localhost:42534/v1/events/dummyOrder"));

        assertThat(fakeAppDirect.lastRequestPath(), is("/v1/events/dummyOrder"));
        assertThat(response.getStatusLine().getStatusCode(), is(200));
        assertThat(EntityUtils.toString(response.getEntity()), is("{\"errorCode\":\"UNKNOWN_ERROR\",\"success\":\"false\"}"));
    }

    @Test
    @Ignore("until the 2 extractors have proper implementations")
    public void addsUserWhenEventIsGood() throws Exception {
        HttpResponse response = anAppDirectHttpClient().execute(get(createSubscription(), "eventUrl", "http://localhost:42534/v1/events/dev-order"));

        List<User> users = toList(userRepository.findAll());
        assertThat(users.size(), is(1));

        User createdUser = users.get(0);

        assertThat(fakeAppDirect.lastRequestPath(), is("/v1/events/dev-order"));
        assertThat(response.getStatusLine().getStatusCode(), is(200));
        assertThat(EntityUtils.toString(response.getEntity()), is("{\"success\":\"true\",\"account-identifier\":\"" + createdUser.getAccount().getId() + "\"))}"));
    }

    @Test
    public void failureWhenOauthSignatureIsInvalid() throws Exception {
        // TODO: eventually support oauth signature verification from auth header, i.e.
        // authorization: OAuth oauth_consumer_key="a-cat-named-nyan-105612", oauth_nonce="-7162822245644540921", oauth_signature="0kL0WN17Sw8yhaAw4PkkFvVEnbY%3D", oauth_signature_method="HMAC-SHA1", oauth_timestamp="1461183771", oauth_version="1.0"
    }

    private String createSubscription() {
        return baseServerUrl() + "/subscription/create/notification";
    }

    private String baseServerUrl() {
        return "http://localhost:" + serverPort;
    }
}
