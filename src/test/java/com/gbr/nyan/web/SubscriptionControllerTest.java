package com.gbr.nyan.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import com.gbr.nyan.appdirect.service.SubscriptionEventService;
import com.gbr.nyan.support.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.success;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

public class SubscriptionControllerTest {
    private SubscriptionController controller;
    private HttpClient httpClient;
    private ObjectMapper jacksonObjectMapper;
    private SubscriptionEventService subscriptionEventService;

    @Before
    public void thisController() throws Exception {
        httpClient = mock(HttpClient.class);
        jacksonObjectMapper = mock(ObjectMapper.class);
        subscriptionEventService = mock(SubscriptionEventService.class);

        controller = new SubscriptionController(httpClient, jacksonObjectMapper, subscriptionEventService);
    }

    @Test
    public void fetchesTheEventUrl() throws Exception {
        controller.create(Optional.of("http://some-event-url"));

        verify(httpClient).get("http://some-event-url");
    }

    @Test
    public void passesTheEventToJackson() throws Exception {
        when(httpClient.get("http://some-event-url")).thenReturn("some-json");

        controller.create(Optional.of("http://some-event-url"));

        verify(jacksonObjectMapper).readValue("some-json", SubscriptionEvent.class);
    }

    @Test
    public void sendsTheParsedEventToTheService() throws Exception {
        SubscriptionEvent someEvent = new SubscriptionEvent();
        when(jacksonObjectMapper.readValue(anyString(), eq(SubscriptionEvent.class))).thenReturn(someEvent);

        controller.create(Optional.of("http://some-event-url"));

        verify(subscriptionEventService).create(someEvent);
    }

    @Test
    public void returnsTheServiceResponseWrappedInHttpOk() throws Exception {
        SubscriptionEvent someEvent = new SubscriptionEvent();
        when(subscriptionEventService.create(any())).thenReturn(success().withAccountIdentifier("some-new-id"));

        ResponseEntity<SubscriptionResponse> response = controller.create(Optional.of("http://some-event-url"));

        assertThat(response.getStatusCode(), is(OK));
        assertThat(response.getBody().getSuccess(), is("true"));
        assertThat(response.getBody().getAccountIdentifier(), is("some-new-id"));
    }
}
