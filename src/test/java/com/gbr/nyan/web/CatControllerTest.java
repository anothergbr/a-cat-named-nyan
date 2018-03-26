package com.gbr.nyan.web;

import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.User;
import com.gbr.nyan.support.HandlebarsRenderer;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.gbr.nyan.domain.Account.Edition.BASIC;
import static com.gbr.nyan.web.support.SecurityContextHelper.logInUser;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CatControllerTest {
    private CatController controller;
    private HandlebarsRenderer viewRenderer;

    @Before
    public void thisController() throws Exception {
        viewRenderer = mock(HandlebarsRenderer.class);
        when(viewRenderer.render(anyString(), any())).thenReturn("the body of the cat");

        controller = new CatController(viewRenderer);
    }

    @Test
    public void passesTheContextToTheViewRenderer() throws Exception {
        logInUser(someBasicUser());

        controller.show();

        Map<String, Object> expectedContext = new HashMap<>();
        expectedContext.put("page-title", "A cat named Nyan");
        expectedContext.put("rendering-cat-page", true);
        expectedContext.put("user-is-logged-in", true);
        expectedContext.put("user-has-active-subscription", true);

        verify(viewRenderer).render("/templates/cat", expectedContext);
    }

    @Test
    public void returnsTheViewResults() throws Exception {
        String responseBody = controller.show();

        assertThat(responseBody, is("the body of the cat"));
    }

    private User someBasicUser() {
        Account account = new Account();
        account.setEdition(BASIC);

        User user = new User();
        user.setEmail("some-email");
        user.setAccount(account);

        return user;
    }
}
