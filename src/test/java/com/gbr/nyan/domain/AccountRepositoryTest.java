package com.gbr.nyan.domain;

import com.gbr.nyan.domain.Account.Edition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.gbr.nyan.domain.Account.Edition.BASIC;
import static com.gbr.nyan.domain.Account.Edition.PREMIUM;
import static com.gbr.nyan.support.Iterables.toList;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AccountRepositoryTest {
    @Autowired
    AccountRepository repository;

    @Before
    public void emptyRepository() {
        repository.deleteAll();
    }

    @Test
    public void savesNewAccount() {
        Account aNewAccount = aNewAccount(PREMIUM);

        repository.save(aNewAccount);
        Account retrievedAccount = repository.findById(aNewAccount.getId()).get();

        assertThat(retrievedAccount.getEdition(), is(PREMIUM));
    }

    @Test
    public void retrievesExistingAccounts() {
        repository.saveAll(asList(aNewAccount(BASIC), aNewAccount(PREMIUM)));

        List<Account> allAccounts = toList(repository.findAll());
        assertThat(allAccounts.size(), is(2));
    }

    private Account aNewAccount(Edition edition) {
        Account newAccount = new Account();
        newAccount.setEdition(edition);

        return newAccount;
    }
}
