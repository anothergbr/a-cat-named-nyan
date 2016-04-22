package com.gbr.nyan.support;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class HandlebarsRendererTest {

    private Handlebars handlebars;
    private HandlebarsRenderer renderer;
    private Template someTemplate;

    @Before
    public void thisRenderer() throws Exception {
        someTemplate = mock(Template.class);
        handlebars = mock(Handlebars.class);
        when(handlebars.compile(anyString())).thenReturn(someTemplate);

        renderer = new HandlebarsRenderer(handlebars);
    }

    @Test
    public void compilesTheHandlebarTemplate() throws Exception {
        renderer.render("some-view", singletonMap("some", "context"));

        verify(handlebars).compile("some-view");
    }

    @Test
    public void appliesContextToTemplate() throws Exception {
        renderer.render("some-view", singletonMap("some", "context"));

        verify(someTemplate).apply(singletonMap("some", "context"));
    }

    @Test
    public void returnsTemplateResults() throws Exception {
        when(someTemplate.apply(anyString())).thenReturn("this is the results of the template");

        String replacedTemplate = renderer.render("some-view", singletonMap("some", "context"));

        assertThat(replacedTemplate, is("this is the results of the template"));
    }
}
