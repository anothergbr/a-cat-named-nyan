package com.gbr.nyan.web;

import com.gbr.nyan.support.HandlebarsRenderer;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.gbr.nyan.web.support.SecurityContextHelper.userNotLoggedIn;
import static java.util.Optional.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class LoginControllerTest {
    private LoginController controller;
    private HandlebarsRenderer viewRenderer;

    @Before
    public void thisController() throws Exception {
        userNotLoggedIn();

        viewRenderer = mock(HandlebarsRenderer.class);
        when(viewRenderer.render(anyString(), anyObject())).thenReturn("the body");

        controller = new LoginController(viewRenderer);
    }

    @Test
    public void passesTheContextToTheViewRenderer() throws Exception {
        controller.login(empty());

        Map<String, Object> expectedContext = new HashMap<>();
        expectedContext.put("page-title", "A cat named Nyan - login");
        expectedContext.put("rendering-login-page", true);
        expectedContext.put("user-is-logged-in", false);
        expectedContext.put("show-error", false);

        verify(viewRenderer).render("/templates/login", expectedContext);
    }

    @Test
    public void returnsTheViewResults() throws Exception {
        String responseBody = controller.login(empty());

        assertThat(responseBody, is("the body"));
    }
}
