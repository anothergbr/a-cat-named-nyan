package com.gbr.nyan.web;

import com.gbr.nyan.appdirect.SubscriptionEventParser;
import com.gbr.nyan.appdirect.SubscriptionEventService;
import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import com.gbr.nyan.web.support.OauthHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.function.Function;

import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.failure;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SubscriptionController {
    private final OauthHttpClient oauthHttpClient;
    private final SubscriptionEventParser subscriptionEventParser;
    private final SubscriptionEventService subscriptionEventService;

    @Autowired
    public SubscriptionController(OauthHttpClient oauthHttpClient, SubscriptionEventParser subscriptionEventParser, SubscriptionEventService subscriptionEventService) {
        this.oauthHttpClient = oauthHttpClient;
        this.subscriptionEventParser = subscriptionEventParser;
        this.subscriptionEventService = subscriptionEventService;
    }

    @RequestMapping(value = "/subscription/create/notification", method = GET, consumes = "*", produces = "application/json")
    public ResponseEntity<SubscriptionResponse> create(@RequestParam Optional<String> eventUrl) throws Exception {
        return getEventAndProcessIt(eventUrl, subscriptionEventService::create);
    }

    @RequestMapping(value = "/subscription/change/notification", method = GET, consumes = "*", produces = "application/json")
    public ResponseEntity<SubscriptionResponse> change(@RequestParam Optional<String> eventUrl) throws Exception {
        return getEventAndProcessIt(eventUrl, subscriptionEventService::change);
    }

    @RequestMapping(value = "/subscription/cancel/notification", method = GET, consumes = "*", produces = "application/json")
    public ResponseEntity<SubscriptionResponse> cancel(@RequestParam Optional<String> eventUrl) throws Exception {
        return getEventAndProcessIt(eventUrl, subscriptionEventService::cancel);
    }

    private ResponseEntity<SubscriptionResponse> getEventAndProcessIt(Optional<String> eventUrl, Function<SubscriptionEvent, SubscriptionResponse> processor) throws Exception {
        if (!eventUrl.isPresent()) {
            return badRequest().body(failure().withErrorCode("INVALID_RESPONSE"));
        }

        String rawEvent = oauthHttpClient.getJson(eventUrl.get());
        SubscriptionEvent event = subscriptionEventParser.fromJson(rawEvent);

        SubscriptionResponse response = processor.apply(event);
        return ok(response);
    }
}
