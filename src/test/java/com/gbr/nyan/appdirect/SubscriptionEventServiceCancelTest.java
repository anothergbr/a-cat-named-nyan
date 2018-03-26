package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.AccountRepository;
import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.gbr.nyan.support.builder.SubscriptionEventBuilder.someEvent;
import static com.gbr.nyan.support.builder.SubscriptionEventBuilder.someStatelessEvent;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class SubscriptionEventServiceCancelTest {
    private AccountExtractor accountExtractor;
    private AccountRepository accountRepository;

    private SubscriptionEventService service;
    private UserRepository userRepository;

    @Before
    public void thisService() {
        accountExtractor = mock(AccountExtractor.class);
        accountRepository = mock(AccountRepository.class);
        userRepository = mock(UserRepository.class);

        service = new SubscriptionEventService(accountExtractor, accountRepository, mock(EventUserExtractor.class), userRepository);
    }

    @Test
    public void returnsUnknownErrorWhenStatelessCancelEventOccurs() {
        SubscriptionEvent statelessEvent = someStatelessEvent().build();

        SubscriptionResponse response = service.cancel(statelessEvent);

        assertThat(response.getSuccess(), is("false"));
        assertThat(response.getErrorCode(), is("UNKNOWN_ERROR"));
    }

    @Test
    public void extractsAccountUpdatesUsersThenDeletesIt() {
        List<User> someUsers = asList(someUser("x@y.com"), someUser("z@y.org"));
        when(userRepository.findAllByAccount(any())).thenReturn(someUsers);

        Account accountToDelete = someAccount("this-will-be-deleted");
        SubscriptionEvent someEvent = someEvent().build();
        when(accountExtractor.whenAccountExists(someEvent)).thenReturn(accountToDelete);

        SubscriptionResponse response = service.cancel(someEvent);

        assertThat(response.getSuccess(), is("true"));
        verify(accountExtractor).whenAccountExists(someEvent);

        verify(userRepository).findAllByAccount(accountToDelete);
        verify(userRepository).saveAll(someUsers);

        verify(accountRepository).deleteById("this-will-be-deleted");
    }

    private User someUser(String email) {
        User user = new User();
        user.setEmail(email);
        return user;
    }

    private Account someAccount(String id) {
        Account account = new Account();
        account.setId(id);
        return account;
    }
}
