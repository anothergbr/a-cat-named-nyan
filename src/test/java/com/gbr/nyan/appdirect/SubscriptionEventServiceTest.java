package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.AccountRepository;
import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static com.gbr.nyan.support.builder.SubscriptionEventBuilder.someEvent;
import static com.gbr.nyan.support.builder.SubscriptionEventBuilder.someStatelessEvent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class SubscriptionEventServiceTest {
    private AccountExtractor accountExtractor;
    private AccountRepository accountRepository;
    private UserExtractor userExtractor;
    private UserRepository userRepository;
    private SubscriptionEventService service;

    @Before
    public void thisService() throws Exception {
        accountExtractor = mock(AccountExtractor.class);
        userExtractor = mock(UserExtractor.class);
        accountRepository = mock(AccountRepository.class);
        userRepository = mock(UserRepository.class);

        when(accountExtractor.fromEvent(any())).thenReturn(someAccount("this-is-the-new-account-id"));
        when(userExtractor.fromEvent(any())).thenReturn(someUser());

        service = new SubscriptionEventService(accountExtractor, accountRepository, userExtractor, userRepository);
    }

    @Test
    public void returnsUnknownErrorWhenStatelessEventIsCreated() {
        SubscriptionEvent statelessEvent = someStatelessEvent().build();

        SubscriptionResponse response = service.create(statelessEvent);

        assertThat(response.getSuccess(), is("false"));
        assertThat(response.getErrorCode(), is("UNKNOWN_ERROR"));
    }

    @Test
    public void sendsEventToExtractorThenSavesResultingAccount() {
        Account aNewAccount = someAccount("this-is-the-new-account-id");
        SubscriptionEvent someEvent = someEvent().build();
        when(accountExtractor.fromEvent(someEvent)).thenReturn(aNewAccount);

        service.create(someEvent);

        verify(accountExtractor).fromEvent(someEvent);
        verify(accountRepository).save(aNewAccount);
    }

    @Test
    public void sendsEventToExtractorThenSavesResultingUser() {
        User aNewUser = someUser();
        SubscriptionEvent someEvent = someEvent().build();
        when(userExtractor.fromEvent(someEvent)).thenReturn(aNewUser);

        service.create(someEvent);

        verify(userExtractor).fromEvent(someEvent);
        verify(userRepository).save(aNewUser);
    }

    @Test
    public void assignsNewAccountToUser() {
        User aNewUser = someUser();
        when(userExtractor.fromEvent(any())).thenReturn(aNewUser);
        when(accountExtractor.fromEvent(any())).thenReturn(someAccount("this-is-the-new-account-id"));

        service.create(someEvent().build());

        assertThat(aNewUser.getAccount().getId(), is("this-is-the-new-account-id"));
    }

    private Account someAccount(String id) {
        return new Account(id);
    }

    private User someUser() {
        return new User();
    }
}
