package com.gbr.nyan.view.partials;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

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
        loggedInDocument = parse(loggedInView());
    }

    @Test
    public void showsLoginButtonWhenNotLoggedIn() throws Exception {
        Document loggedOutDocument = parse(loggedOutView());

        Element loginButton = loggedOutDocument.select("a[href=/login]").first();
        assertThat(loginButton.text(), is("Login"));
    }

    @Test
    public void showsLogoutAndHidesLoginWhenLoggedIn() {
        Element logoutButton = loggedInDocument.select("a[href=/logout]").first();
        assertThat(logoutButton.text(), is("Logout"));

        Element loginButton = loggedInDocument.select("a[href=/login]").first();
        assertThat(loginButton, is(nullValue()));
    }

    @Test
    public void onlyShowsCatButtonWhenLoggedIn() throws Exception {
        Element catLinkWhenLoggedIn = loggedInDocument.select("#the-cat a").first();
        assertThat(catLinkWhenLoggedIn.attr("href"), is("/cat"));

        Element catLinkWhenLoggedOut = parse(loggedOutView()).select("#the-cat a").first();
        assertThat(catLinkWhenLoggedOut, is(nullValue()));
    }

    @Test
    public void hasProperBrand() {
        assertThat(loggedInDocument.select(".navbar-brand").first().text(), is("A cat named Nyan"));
    }

    private String loggedOutView() throws Exception {
        return someView(false);
    }

    private String loggedInView() throws Exception {
        return someView(true);
    }

    private String someView(boolean userIsLoggedIn) throws Exception {
        Map<String, Object> viewContext = new HashMap<>();
        viewContext.put("user-is-logged-in", userIsLoggedIn);

        return aRenderer().render("/templates/partials/nav-bar", viewContext);
    }
}
