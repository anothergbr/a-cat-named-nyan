package com.gbr.nyan.openid;

import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class OpenIdUserDetailsServiceTest {
    private TokenUserExtractor userExtractor;
    private UserRepository userRepository;
    private OpenIdUserDetailsService service;

    @Before
    public void thisService() {
        userExtractor = mock(TokenUserExtractor.class);
        userRepository = mock(UserRepository.class);

        service = new OpenIdUserDetailsService(userExtractor, userRepository);
    }

    @Test
    public void fetchesExistingUserFromTheRepo() {
        User existingUser = someUser();
        OpenIDAuthenticationToken token = someToken("some-url");
        when(userRepository.findByOpenIdUrl("some-url")).thenReturn(existingUser);

        User retrievedUser = (User) service.loadUserDetails(token);

        assertThat(retrievedUser, is(existingUser));
    }

    @Test
    public void savesAndReturnsNewUserWhenItDoesNotYetExist() {
        User newUser = someUser();
        OpenIDAuthenticationToken token = someToken("url-of-some-new-user");
        when(userExtractor.fromOpenIdToken(token)).thenReturn(newUser);

        User retrievedUser = (User) service.loadUserDetails(token);

        assertThat(retrievedUser, is(newUser));
        verify(userRepository).save(newUser);
    }

    @Test(expected = NotYetImplementedException.class)
    public void doesNotImplementLoadUserByUserNameBecauseItsNotNeeded() {
        service.loadUserByUsername("any-id");
    }

    private User someUser() {
        return new User();
    }

    private OpenIDAuthenticationToken someToken(String openIdUrl) {
        return new OpenIDAuthenticationToken(OpenIDAuthenticationStatus.SUCCESS, openIdUrl, "some-message", emptyList());
    }

}