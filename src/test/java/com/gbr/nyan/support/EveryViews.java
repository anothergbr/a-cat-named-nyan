package com.gbr.nyan.support;

import org.jsoup.nodes.Document;

import java.util.List;

import static com.gbr.nyan.support.SafeMatchers.hasItems;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class EveryViews {
    public static void shouldIncludeBootstrap(Document document) {
        List<String> cssHrefs = document.select("link[rel=stylesheet]").stream()
                .map(e -> e.attr("href"))
                .collect(toList());

        assertThat(cssHrefs, hasItems(
                containsString("/bootstrap/3.3.7/css/bootstrap.min.css"),
                containsString("/bootstrap/3.3.7/css/bootstrap-theme.min.css")));
    }
}
