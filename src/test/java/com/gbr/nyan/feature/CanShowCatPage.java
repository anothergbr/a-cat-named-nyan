package com.gbr.nyan.feature;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CanShowCatPage {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @Test
    public void redirectsAnonymousUsersToLogin() throws Exception {
        mockMvc.perform(get("/cat"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "http://localhost/login"));
    }

    @Test
    public void allowsLoggedInUsers() throws Exception {
        mockMvc.perform(get("/cat").with(loggedInUser()))
                .andExpect(status().isOk());
    }

    private SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor loggedInUser() {
        return user("some-user").roles("USER");
    }
}
