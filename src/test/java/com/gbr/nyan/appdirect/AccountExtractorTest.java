package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.domain.Account;
import org.junit.Test;

import static com.gbr.nyan.domain.Account.Edition.PREMIUM;
import static com.gbr.nyan.support.builder.SubscriptionEventBuilder.someEvent;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class AccountExtractorTest {
    private AccountExtractor extractor = new AccountExtractor();

    @Test
    public void readsEditionFromEvent() throws Exception {
        Account account = extractor.fromEvent(someEvent().withEdition(PREMIUM).build());
        assertThat(account.getEdition(), is(PREMIUM));
    }

    @Test
    public void doesNotSetAccountIdInGeneral() throws Exception {
        Account account = extractor.fromEvent(someEvent().build());
        assertThat(account.getId(), is(nullValue()));
    }

    @Test
    public void setsAccountIdForChangeEvents() throws Exception {
        Account account = extractor.fromChangeEvent(someEvent().withAccountIdentifier("the-account-id").build());
        assertThat(account.getId(), is("the-account-id"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIfAccountIsMissingInChangeEvents() throws Exception {
        SubscriptionEvent eventMissingAccount = someEvent().build();
        eventMissingAccount.getPayload().setAccount(null);

        extractor.fromChangeEvent(eventMissingAccount);
    }
}
