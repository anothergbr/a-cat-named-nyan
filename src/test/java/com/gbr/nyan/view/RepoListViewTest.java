package com.gbr.nyan.view;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.gbr.nyan.support.HandlebarsRenderer.aRenderer;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RepoListViewTest {
    private Document document;

    @Before
    public void thisView() throws Exception {
        Map<String, Object> viewContext = new HashMap<>();
        viewContext.put("page-title", "some-title");
        viewContext.put("rendering-repo-list", true);

        document = Jsoup.parse(aRenderer().render("/templates/repo-list", viewContext));
    }

    @Test
    public void hasProperTitle() {
        assertThat(document.title(), is("some-title"));
    }

    @Test
    public void repoListItemIsActiveInNavbar() {
        Element activeElement = document.select("#navbar li.active").first();
        assertThat(activeElement.text(), is("Content of the repositories"));
    }
}
