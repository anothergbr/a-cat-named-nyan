package com.gbr.nyan.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.gbr.nyan.domain.Account.Edition.PREMIUM;
import static com.gbr.nyan.support.Iterables.toList;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK)
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void emptyRepository() {
        repository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void savesNewUserWithAccount() throws Exception {
        User aNewUser = aNewUser("some@email.com", "Gerry");
        aNewUser.setAccount(aSavedAccount());

        repository.save(aNewUser);
        User retrievedUser = repository.findOne(aNewUser.getEmail());

        assertThat(retrievedUser.getFirstName(), is("Gerry"));
        assertThat(retrievedUser.getAccount(), is(notNullValue()));
    }

    @Test
    public void retrievesExistingUsers() throws Exception {
        repository.save(asList(aNewUser("test@email.ca", "Richard"), aNewUser("other@email.jp", "Stephanie")));

        List<User> allUsers = toList(repository.findAll());
        assertThat(allUsers.size(), is(2));
    }

    private User aNewUser(String email, String firstName) {
        User newUser = new User(email);
        newUser.setFirstName(firstName);

        return newUser;
    }

    private Account aSavedAccount() {
        Account account = new Account();
        account.setEdition(PREMIUM);
        accountRepository.save(account);

        return account;
    }
}
