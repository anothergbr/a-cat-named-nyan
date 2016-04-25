package com.gbr.nyan.feature;

import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.AccountRepository;
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

import static com.gbr.nyan.domain.Account.Edition.BASIC;
import static com.gbr.nyan.domain.Account.Edition.PREMIUM;
import static com.gbr.nyan.support.HttpClientHelper.anAppDirectHttpClient;
import static com.gbr.nyan.support.HttpClientHelper.get;
import static com.gbr.nyan.support.Iterables.toList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CanChangeSubscription {
    @LocalServerPort
    private int serverPort;
    private FakeAppDirect fakeAppDirect;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setup() throws Exception {
        userRepository.deleteAll();
        accountRepository.deleteAll();

        fakeAppDirect = FakeAppDirect.create(42534).start();
    }

    @After
    public void stopFakeServer() {
        fakeAppDirect.stop();
    }

    @Test
    public void respondsToStatelessEventsWithWellFormedErrorResponse() throws Exception {
        HttpResponse response = anAppDirectHttpClient().execute(get(changeSubscription(), "eventUrl", "http://localhost:42534/v1/events/dummyChange"));

        assertThat(fakeAppDirect.lastRequestPath(), is("/v1/events/dummyChange"));
        assertThat(response.getStatusLine().getStatusCode(), is(200));
        assertThat(EntityUtils.toString(response.getEntity()), is("{\"errorCode\":\"UNKNOWN_ERROR\",\"success\":\"false\"}"));
    }

    @Test
    @Ignore("until I get a sample subscription change!")
    public void updatesUserEditionAccordingToChangeEvent() throws Exception {
        Account existingBasicAccount = saveAnAccount(BASIC);
        saveAUserWith("gabriel.x@gmail.com", existingBasicAccount);

        HttpResponse response = anAppDirectHttpClient().execute(get(changeSubscription(), "eventUrl", "http://localhost:42534/v1/events/dev-change"));
        assertThat(fakeAppDirect.lastRequestPath(), is("/v1/events/dev-change"));
        assertThat(response.getStatusLine().getStatusCode(), is(200));

        List<User> users = toList(userRepository.findAll());
        assertThat(users.size(), is(1));

        User premiumUser = users.get(0);
        assertThat(premiumUser.getAccount().getEdition(), is(PREMIUM));
        assertThat(premiumUser.getEmail(), is("gabriel.x@gmail.com"));

        assertThat(EntityUtils.toString(response.getEntity()), is("{\"success\":\"true\"}"));
    }

    private Account saveAnAccount(Account.Edition edition) {
        Account account = new Account();
        account.setEdition(edition);

        accountRepository.save(account);

        return account;
    }

    private User saveAUserWith(String email, Account account) {
        User user = new User();
        user.setEmail(email);
        user.setAccount(account);

        userRepository.save(user);

        return user;
    }

    private String changeSubscription() {
        return baseServerUrl() + "/subscription/change/notification";
    }

    private String baseServerUrl() {
        return "http://localhost:" + serverPort;
    }
}
