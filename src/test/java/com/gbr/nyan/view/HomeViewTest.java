package com.gbr.nyan.view;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.gbr.nyan.support.EveryViews.shouldIncludeBootstrap;
import static com.gbr.nyan.support.HandlebarsRenderer.aRenderer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HomeViewTest {
    private Document document;

    @Before
    public void thisView() throws Exception {
        Map<String, Object> viewContext = new HashMap<>();
        viewContext.put("page-title", "A very good title");
        viewContext.put("rendering-home-page", true);

        document = Jsoup.parse(aRenderer().render("/templates/home", viewContext));
    }

    @Test
    public void includesBootstrap() {
        shouldIncludeBootstrap(document);
    }

    @Test
    public void hasProperTitle() {
        assertThat(document.title(), is("A very good title"));
    }

    @Test
    public void homeItemIsActiveInNavbar() {
        Element activeElement = document.select("#navbar li.active").first();
        assertThat(activeElement.text(), is("Home"));
    }

    @Test
    public void suggestsToBuyTheApp() {
        Element firstTitle = document.getElementsByTag("h1").first();
        assertThat(firstTitle.text(), is("A cat named Nyan can now be bought!"));

        Element bigBuyButton = document.select("a.btn-lg").first();
        assertThat(bigBuyButton.text(), is("Buy the app \u00BB"));
        assertThat(bigBuyButton.attr("href"), is("https://gabrielspub.byappdirect.com"));
    }
}
