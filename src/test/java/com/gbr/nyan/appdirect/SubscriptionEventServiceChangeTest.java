package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.AccountRepository;
import com.gbr.nyan.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static com.gbr.nyan.support.builder.SubscriptionEventBuilder.someEvent;
import static com.gbr.nyan.support.builder.SubscriptionEventBuilder.someStatelessEvent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class SubscriptionEventServiceChangeTest {
    private AccountExtractor accountExtractor;
    private AccountRepository accountRepository;

    private SubscriptionEventService service;

    @Before
    public void thisService() throws Exception {
        accountExtractor = mock(AccountExtractor.class);
        accountRepository = mock(AccountRepository.class);

        service = new SubscriptionEventService(accountExtractor, accountRepository, mock(EventUserExtractor.class), mock(UserRepository.class));
    }

    @Test
    public void returnsUnknownErrorWhenStatelessChangeEventOccurs() {
        SubscriptionEvent statelessEvent = someStatelessEvent().build();

        SubscriptionResponse response = service.change(statelessEvent);

        assertThat(response.getSuccess(), is("false"));
        assertThat(response.getErrorCode(), is("UNKNOWN_ERROR"));
    }

    @Test
    public void sendsEventToExtractorThenUpdatesExistingAccount() {
        Account existingAccountWithNewEdition = someAccount("this-is-an-existing-account-id");
        SubscriptionEvent someEvent = someEvent().build();
        when(accountExtractor.fromChangeEvent(someEvent)).thenReturn(existingAccountWithNewEdition);

        SubscriptionResponse response = service.change(someEvent);

        assertThat(response.getSuccess(), is("true"));
        verify(accountExtractor).fromChangeEvent(someEvent);
        verify(accountRepository).save(existingAccountWithNewEdition);
    }

    private Account someAccount(String id) {
        Account account = new Account();
        account.setId(id);
        return account;
    }
}
