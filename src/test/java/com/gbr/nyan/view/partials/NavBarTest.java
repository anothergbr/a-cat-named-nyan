package com.gbr.nyan.view.partials;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.gbr.nyan.support.HandlebarsRenderer.aRenderer;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.jsoup.Jsoup.parse;
import static org.junit.Assert.assertThat;

public class NavBarTest {
    private Document loggedInDocument;

    @Before
    public void thisView() throws Exception {
        loggedInDocument = parse(aRenderer().render("/templates/partials/nav-bar", loggedInView()));
    }

    @Test
    public void showsLoginButtonWhenNotLoggedIn() throws IOException {
        Document loggedOutDoc = parse(aRenderer().render("/templates/partials/nav-bar", loggedOutView()));

        Element loginButton = loggedOutDoc.select("a[href=/login]").first();
        assertThat(loginButton.text(), is("Login"));
    }

    @Test
    public void showsLogoutAndHidesLoginWhenLoggedIn() throws IOException {
        Element logoutButton = loggedInDocument.select("a[href=/logout]").first();
        assertThat(logoutButton.text(), is("Logout"));

        Element loginButton = loggedInDocument.select("a[href=/login]").first();
        assertThat(loginButton, is(nullValue()));
    }

    @Test
    public void hasProperBrand() {
        assertThat(loggedInDocument.select(".navbar-brand").first().text(), is("A cat named Nyan"));
    }

    private Map<String, Object> loggedOutView() {
        return someViewContext(false);
    }

    private Map<String, Object> loggedInView() {
        return someViewContext(true);
    }

    private Map<String, Object> someViewContext(boolean userIsLoggedIn) {
        Map<String, Object> viewContext = new HashMap<>();
        viewContext.put("user-is-logged-in", userIsLoggedIn);
        return viewContext;
    }
}
