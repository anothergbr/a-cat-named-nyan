package com.gbr.nyan.web;

import com.gbr.nyan.support.HandlebarsRenderer;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class HomeControllerTest {
    private HomeController controller;
    private HandlebarsRenderer viewRenderer;

    @Before
    public void thisController() throws Exception {
        viewRenderer = mock(HandlebarsRenderer.class);
        when(viewRenderer.render(anyString(), anyObject())).thenReturn("the body");

        controller = new HomeController(viewRenderer);
    }

    @Test
    public void passesTheContextToTheViewRenderer() throws Exception {
        controller.root();

        verify(viewRenderer).render("/templates/home", null);
    }

    @Test
    public void returnsTheViewResults() throws Exception {
        String responseBody = controller.root();

        assertThat(responseBody, is("the body"));
    }
}
