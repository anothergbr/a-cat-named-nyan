package com.gbr.nyan.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ExampleControllerTest {
    @Autowired
    private TestRestTemplate httpClient;

    @Test
    public void returns200OnRoot() throws Exception {
        ResponseEntity<String> rootResponse = httpClient.getForEntity("/", String.class);
        assertThat(rootResponse.getStatusCode(), is(OK));
    }

    @Test
    public void returnsHelloWorldOnRoot() throws Exception {
        ResponseEntity<String> rootResponse = httpClient.getForEntity("/", String.class);
        assertThat(rootResponse.getBody(), containsString("Hello World!"));
    }
}
