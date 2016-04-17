package com.gbr.nyan.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HomeControllerTest {
    @Autowired
    private TestRestTemplate httpClient;
    private ResponseEntity<String> rootResponse;

    @Before
    public void thisController() {
        rootResponse = httpClient.getForEntity("/", String.class);
    }

    @Test
    public void returnsOk() throws Exception {
        assertThat(rootResponse.getStatusCode(), is(OK));
    }

    @Test
    public void returnsUtf8Html() throws Exception {
        String contentType = rootResponse.getHeaders().getContentType().toString();
        assertThat(contentType, is("text/html;charset=UTF-8"));
    }
}
