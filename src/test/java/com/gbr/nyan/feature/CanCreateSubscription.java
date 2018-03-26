package com.gbr.nyan.feature;

import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import com.gbr.nyan.support.FakeAppDirect;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.gbr.nyan.domain.Account.Edition.BASIC;
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
    public void setup() throws Exception {
        userRepository.deleteAll();
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
    public void addsUserAndAccountOnCreationEvent() throws Exception {
        HttpResponse response = anAppDirectHttpClient().execute(get(createSubscription(), "eventUrl", "http://localhost:42534/v1/events/dev-order"));
        assertThat(fakeAppDirect.lastRequestPath(), is("/v1/events/dev-order"));
        assertThat(response.getStatusLine().getStatusCode(), is(200));

        List<User> users = toList(userRepository.findAll());
        assertThat(users.size(), is(1));

        User createdUser = users.get(0);
        assertThat(createdUser.getAccount().getEdition(), is(BASIC));
        assertThat(createdUser.getFullName(), is("Gabriel SomeLastName"));
        assertThat(createdUser.getUuid(), is("dac5f67b-7da7-4603-bb1b-3fd507509081"));
        assertThat(createdUser.getEmail(), is("gabriel.x@gmail.com"));
        assertThat(createdUser.getOpenIdUrl(), is("https://gabrielspub-test.byappdirect.com/openid/id/dac5f67b-7da7-4603-bb1b-3fd507509081"));

        assertThat(EntityUtils.toString(response.getEntity()), is("{\"accountIdentifier\":\"" + createdUser.getAccount().getId() + "\",\"success\":\"true\"}"));
    }

    @Test
    public void updatesAnExistingUserOnCreationEvent() throws Exception {
        saveAUserWith("gabriel.x@gmail.com");

        HttpResponse response = anAppDirectHttpClient().execute(get(createSubscription(), "eventUrl", "http://localhost:42534/v1/events/dev-order"));
        assertThat(response.getStatusLine().getStatusCode(), is(200));

        List<User> users = toList(userRepository.findAll());
        assertThat(users.size(), is(1));

        User updatedUser = userRepository.findByEmail("gabriel.x@gmail.com");
        assertThat(updatedUser.getAccount().getEdition(), is(BASIC));
        assertThat(updatedUser.getUuid(), is("dac5f67b-7da7-4603-bb1b-3fd507509081"));
    }

    private User saveAUserWith(String email) {
        User user = new User();
        user.setEmail(email);

        userRepository.save(user);

        return user;
    }

    private String createSubscription() {
        return baseServerUrl() + "/subscription/create/notification";
    }

    private String baseServerUrl() {
        return "http://localhost:" + serverPort;
    }
}
