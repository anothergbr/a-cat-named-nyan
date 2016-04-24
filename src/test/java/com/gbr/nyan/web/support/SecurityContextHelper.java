package com.gbr.nyan.web.support;

import com.gbr.nyan.domain.User;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import static java.util.Collections.singletonList;

public class SecurityContextHelper {
    public static void userLoggedIn() {
        TestingAuthenticationToken testAuthToken = createTestAuthToken(new User(), "ROLE_USER");
        setAuthTokenInContext(testAuthToken);
    }

    public static void userNotLoggedIn() {
        TestingAuthenticationToken testAuthToken = createTestAuthToken("anonymous-user", null);
        setAuthTokenInContext(testAuthToken);
    }

    public static void logInUser(User user) {
        TestingAuthenticationToken testAuthToken = createTestAuthToken(user, "ROLE_USER");
        setAuthTokenInContext(testAuthToken);
    }

    public static void nullAuthenticationToken() {
        setAuthTokenInContext(null);
    }

    private static TestingAuthenticationToken createTestAuthToken(Object principal, String theOneRole) {
        return new TestingAuthenticationToken(principal, null, theOneRole == null ? null : singletonList(new SimpleGrantedAuthority(theOneRole)));
    }

    private static void setAuthTokenInContext(TestingAuthenticationToken loggedInUserToken) {
        SecurityContextImpl contextWithALoggedInUser = new SecurityContextImpl();
        contextWithALoggedInUser.setAuthentication(loggedInUserToken);

        SecurityContextHolder.setContext(contextWithALoggedInUser);
    }
}
