package com.gbr.nyan.web;

import com.gbr.nyan.support.HandlebarsRenderer;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CatControllerTest {
    private CatController controller;
    private HandlebarsRenderer viewRenderer;

    @Before
    public void thisController() throws Exception {
        viewRenderer = mock(HandlebarsRenderer.class);
        when(viewRenderer.render(anyString(), anyObject())).thenReturn("the body of the cat");

        controller = new CatController(viewRenderer);
    }

    @Test
    public void passesTheContextToTheViewRenderer() throws Exception {
        controller.show();

        Map<String, Object> expectedContext = new HashMap<>();
        expectedContext.put("page-title", "A cat named Nyan");
        expectedContext.put("rendering-cat-page", true);
        expectedContext.put("user-is-logged-in", false);

        verify(viewRenderer).render("/templates/cat", expectedContext);
    }

    @Test
    public void returnsTheViewResults() throws Exception {
        String responseBody = controller.show();

        assertThat(responseBody, is("the body of the cat"));
    }
}
