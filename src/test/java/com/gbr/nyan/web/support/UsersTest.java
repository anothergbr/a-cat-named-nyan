package com.gbr.nyan.web.support;

import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsersTest {

    @Test
    public void isLoggedInWhenRoleUserIsPresent() {
        fakeALoggedInUser("ROLE_USER");

        assertTrue(Users.isUserLoggedIn());
    }

    @Test
    public void isNotLoggedInWhenRoleUserIsAbsent() {
        fakeALoggedInUser("ROLE_SOME_OTHER");

        assertFalse(Users.isUserLoggedIn());
    }

    private void fakeALoggedInUser(String theOneRole) {
        TestingAuthenticationToken loggedInUserToken = new TestingAuthenticationToken("some-user", null, singletonList(new SimpleGrantedAuthority(theOneRole)));
        SecurityContextImpl contextWithALoggedInUser = new SecurityContextImpl();
        contextWithALoggedInUser.setAuthentication(loggedInUserToken);

        SecurityContextHolder.setContext(contextWithALoggedInUser);
    }
}