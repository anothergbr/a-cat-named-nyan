package com.gbr.nyan.web;

import com.gbr.nyan.appdirect.SubscriptionEventParser;
import com.gbr.nyan.appdirect.SubscriptionEventService;
import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import com.gbr.nyan.support.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.failure;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SubscriptionController {
    private final HttpClient jsonHttpClient;
    private final SubscriptionEventParser subscriptionEventParser;
    private final SubscriptionEventService subscriptionEventService;

    @Autowired
    public SubscriptionController(HttpClient jsonHttpClient, SubscriptionEventParser subscriptionEventParser, SubscriptionEventService subscriptionEventService) {
        this.jsonHttpClient = jsonHttpClient;
        this.subscriptionEventParser = subscriptionEventParser;
        this.subscriptionEventService = subscriptionEventService;
    }

    @RequestMapping(value = "/subscription/create/notification", method = GET, consumes = "*", produces = "application/json")
    public ResponseEntity<SubscriptionResponse> create(@RequestParam Optional<String> eventUrl) throws Exception {
        if (!eventUrl.isPresent()) {
            return badRequest().body(failure().withErrorCode("INVALID_RESPONSE"));
        }

        String rawCreateEvent = jsonHttpClient.get(eventUrl.get());
        SubscriptionEvent createEvent = subscriptionEventParser.fromJson(rawCreateEvent);

        SubscriptionResponse response = subscriptionEventService.create(createEvent);
        return ok(response);
    }
}
