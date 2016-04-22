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
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    private Account existingAccount;

    @Before
    public void emptyRepositoryAndCreateOneAccount() {
        repository.deleteAll();
        accountRepository.deleteAll();

        existingAccount = aSavedAccount();
    }

    @Test
    public void savesNewUser() throws Exception {
        User aNewUser = aNewUser("some@email.com", existingAccount, "Gerry");

        repository.save(aNewUser);
        User retrievedUser = repository.findOne(aNewUser.getEmail());

        assertThat(retrievedUser.getFirstName(), is("Gerry"));
        assertThat(retrievedUser.getAccount(), is(notNullValue()));
    }

    @Test
    public void retrievesExistingUsers() throws Exception {
        repository.save(asList(aNewUser("test@email.ca", existingAccount, "Richard"), aNewUser("other@email.jp", existingAccount, "Stephanie")));

        List<User> allUsers = toList(repository.findAll());
        assertThat(allUsers.size(), is(2));
    }

    private User aNewUser(String email, Account account, String firstName) {
        User newUser = new User(email);
        newUser.setAccount(account);
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
