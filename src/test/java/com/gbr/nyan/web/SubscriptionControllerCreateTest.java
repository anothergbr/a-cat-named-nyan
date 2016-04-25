package com.gbr.nyan.web;

import com.gbr.nyan.appdirect.SubscriptionEventParser;
import com.gbr.nyan.appdirect.SubscriptionEventService;
import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import com.gbr.nyan.web.support.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.success;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class SubscriptionControllerCreateTest {
    private SubscriptionController controller;
    private HttpClient httpClient;
    private SubscriptionEventParser eventParser;
    private SubscriptionEventService subscriptionEventService;

    @Before
    public void thisController() throws Exception {
        httpClient = mock(HttpClient.class);
        eventParser = mock(SubscriptionEventParser.class);
        subscriptionEventService = mock(SubscriptionEventService.class);

        controller = new SubscriptionController(httpClient, eventParser, subscriptionEventService);
    }

    @Test
    public void failsWhenNoEventUrlIsPassed() throws Exception {
        ResponseEntity<SubscriptionResponse> response = controller.create(Optional.empty());

        assertThat(response.getStatusCode(), is(BAD_REQUEST));
        assertThat(response.getBody().getSuccess(), is("false"));
        assertThat(response.getBody().getErrorCode(), is("INVALID_RESPONSE"));
    }

    @Test
    public void fetchesTheEventUrl() throws Exception {
        controller.create(Optional.of("http://some-event-url"));

        verify(httpClient).get("http://some-event-url");
    }

    @Test
    public void parsesTheEvent() throws Exception {
        when(httpClient.get("http://some-event-url")).thenReturn("some-json");

        controller.create(Optional.of("http://some-event-url"));

        verify(eventParser).fromJson("some-json");
    }

    @Test
    public void sendsTheParsedEventToTheService() throws Exception {
        SubscriptionEvent someEvent = new SubscriptionEvent();
        when(eventParser.fromJson(anyString())).thenReturn(someEvent);

        controller.create(Optional.of("http://some-event-url"));

        verify(subscriptionEventService).create(someEvent);
    }

    @Test
    public void returnsTheServiceResponseWrappedInHttpOk() throws Exception {
        when(subscriptionEventService.create(any())).thenReturn(success().withAccountIdentifier("some-new-id"));

        ResponseEntity<SubscriptionResponse> response = controller.create(Optional.of("http://some-event-url"));

        assertThat(response.getStatusCode(), is(OK));
        assertThat(response.getBody().getSuccess(), is("true"));
        assertThat(response.getBody().getAccountIdentifier(), is("some-new-id"));
    }
}