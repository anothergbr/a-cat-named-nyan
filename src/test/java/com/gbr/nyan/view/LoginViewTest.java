package com.gbr.nyan.view;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.gbr.nyan.support.EveryViews.shouldIncludeBootstrap;
import static com.gbr.nyan.support.HandlebarsRenderer.aRenderer;
import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.*;
import static org.jsoup.Jsoup.parse;
import static org.junit.Assert.assertThat;

public class LoginViewTest {
    private Document document;

    @Before
    public void thisView() throws Exception {
        Map<String, Object> viewContext = new HashMap<>();
        viewContext.put("page-title", "The login");
        viewContext.put("rendering-login-page", true);
        viewContext.put("show-error", false);
        viewContext.put("show-logout-success", false);

        document = parse(aRenderer().render("/templates/login", viewContext));
    }

    @Test
    public void includesBootstrap() {
        shouldIncludeBootstrap(document);
    }

    @Test
    public void hasProperTitle() {
        assertThat(document.title(), is("The login"));
    }

    @Test
    public void loginButtonIsDisabledInNavbar() {
        Element loginButton = document.select("a[href=login]").first();
        assertThat(loginButton.hasClass("disabled"), is(true));

        Element activeElement = document.select("#navbar li.active").first();
        assertThat(activeElement, is(nullValue()));
    }

    @Test
    public void hasFormToSignInWithAppDirectOpenId() {
        Element openIdForm = document.select("form#appdirect-openid-signin").first();
        assertThat(openIdForm.attr("action"), is("/login/openid"));
        assertThat(openIdForm.attr("method"), is("post"));

        Element hiddenOpenId = openIdForm.select("input[name=openid_identifier]").first();
        assertThat(hiddenOpenId.attr("type"), is("hidden"));
        assertThat(hiddenOpenId.val(), is("https://gabrielspub.byappdirect.com/openid/id"));

        Element submitLabel = openIdForm.select("label[for=submit]").first();
        assertThat(submitLabel.text(), is("log in with"));
    }

    @Test
    public void showsErrorMessageWhenToldTo() throws Exception {
        document = parse(aRenderer().render("/templates/login", singletonMap("show-error", true)));

        Element errorRow = document.select("#error-row").first();
        assertThat(errorRow.text(), startsWith("Could not log you in"));
    }

    @Test
    public void showsLogoutSuccessfulWhenToldTo() throws Exception {
        document = parse(aRenderer().render("/templates/login", singletonMap("show-logout-success", true)));

        Element logoutSuccessRow = document.select("#logout-success-row").first();
        assertThat(logoutSuccessRow.text(), startsWith("Successfully logged you out"));
    }
}
