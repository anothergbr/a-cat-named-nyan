package com.gbr.nyan.web.support;

import org.junit.Test;

import static com.gbr.nyan.web.support.SecurityContextHelper.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsersTest {

    @Test
    public void isLoggedInWhenUserIsPresent() {
        userLoggedIn();
        assertTrue(Users.userIsLoggedIn());
    }

    @Test
    public void isNotLoggedInWhenAnonymousUserIsInContext() {
        userNotLoggedIn();
        assertFalse(Users.userIsLoggedIn());
    }

    @Test
    public void isNotLoggedInWhenNothingIsInContext() {
        nullAuthenticationToken();
        assertFalse(Users.userIsLoggedIn());
    }
}
