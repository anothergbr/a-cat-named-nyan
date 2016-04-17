package com.gbr.nyan.view;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gbr.nyan.support.HandlebarsRenderer.aRenderer;
import static com.gbr.nyan.support.SafeMatchers.hasItems;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HomeViewTest {
    private Document document;

    @Before
    public void thisView() throws Exception {
        Map<String, String> viewContext = new HashMap<>();
        document = Jsoup.parse(aRenderer().render("/templates/home", viewContext));
    }

    @Test
    public void includesBootstrap() {
        List<String> cssHrefs = document.select("link[rel=stylesheet]").stream()
                .map(e -> e.attr("href"))
                .collect(toList());

        assertThat(cssHrefs, hasItems(
                containsString("/bootstrap/3.3.6/css/bootstrap.min.css"),
                containsString("/bootstrap/3.3.6/css/bootstrap-theme.min.css")));
    }

    @Test
    public void saysHelloWorld() {
        Element firstTitle = document.getElementsByTag("h1").first();
        assertThat(firstTitle.text(), is("Hello world!"));
    }
}
