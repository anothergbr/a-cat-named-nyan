package com.gbr.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleControllerTest {
    private MockMvc mockedServer;

    @Before
    public void setupFakeController() throws Exception {
        mockedServer = MockMvcBuilders.standaloneSetup(new ExampleController()).build();
    }

    @Test
    public void returns200OnRoot() throws Exception {
        mockedServer.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnsHelloWorldOnRoot() throws Exception {
        mockedServer.perform(get("/"))
                .andExpect(content().string(is("Hello World!")));
    }
}
