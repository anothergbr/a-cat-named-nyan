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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.web.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.gbr.nyan.domain.Account.Edition.BASIC;
import static com.gbr.nyan.support.HttpClientHelper.anAppDirectHttpClient;
import static com.gbr.nyan.support.HttpClientHelper.get;
import static com.gbr.nyan.support.Iterables.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CanCancelSubscription {
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
        HttpResponse response = anAppDirectHttpClient().execute(get(cancelSubscription(), "eventUrl", "http://localhost:42534/v1/events/dummyCancel"));

        assertThat(fakeAppDirect.lastRequestPath(), is("/v1/events/dummyCancel"));
        assertThat(response.getStatusLine().getStatusCode(), is(200));
        assertThat(EntityUtils.toString(response.getEntity()), is("{\"errorCode\":\"UNKNOWN_ERROR\",\"success\":\"false\"}"));
    }

    @Test
    public void deletesAccountAccordingToCancelEvent() throws Exception {
        Account accountToDelete = saveAnAccount(BASIC);
        saveAUserWith("gabriel.x@gmail.com", accountToDelete);

        HttpResponse response = anAppDirectHttpClient().execute(get(cancelSubscription(), "eventUrl", "http://localhost:42534/v1/events/dev-cancel?account-id=" + accountToDelete.getId()));
        assertThat(fakeAppDirect.lastRequestPath(), startsWith("/v1/events/dev-cancel"));
        assertThat(response.getStatusLine().getStatusCode(), is(200));

        List<Account> accounts = toList(accountRepository.findAll());
        assertThat(accounts.size(), is(0));

        List<User> users = toList(userRepository.findAll());
        assertThat(users.size(), is(1));

        User userWithoutAccount = users.get(0);
        assertThat(userWithoutAccount.getAccount(), is(nullValue()));
        assertThat(userWithoutAccount.getEmail(), is("gabriel.x@gmail.com"));

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

    private String cancelSubscription() {
        return baseServerUrl() + "/subscription/cancel/notification";
    }

    private String baseServerUrl() {
        return "http://localhost:" + serverPort;
    }
}
