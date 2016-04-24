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

public class CatViewTest {
    private Document document;

    @Before
    public void thisView() throws Exception {
        Map<String, Object> viewContext = new HashMap<>();
        viewContext.put("page-title", "The cat title");
        viewContext.put("rendering-cat-page", true);

        document = Jsoup.parse(aRenderer().render("/templates/cat", viewContext));
    }

    @Test
    public void includesBootstrap() {
        shouldIncludeBootstrap(document);
    }

    @Test
    public void hasProperTitle() {
        assertThat(document.title(), is("The cat title"));
    }

    @Test
    public void catItemIsActiveInNavbar() {
        Element activeElement = document.select("#navbar li.active").first();
        assertThat(activeElement.id(), is("the-cat"));
    }
}
