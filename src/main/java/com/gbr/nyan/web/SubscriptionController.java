package com.gbr.nyan.web;

import com.gbr.nyan.appdirect.SubscriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Optional;

import static com.gbr.nyan.appdirect.SubscriptionResponse.failure;
import static com.gbr.nyan.appdirect.SubscriptionResponse.success;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SubscriptionController {

    @RequestMapping(value = "/subscription/create/notification", method = GET, consumes = "*", produces = "application/json")
    public ResponseEntity<SubscriptionResponse> create(@RequestParam Optional<String> eventUrl) throws IOException {
        if (!eventUrl.isPresent()) {
            return badRequest().body(failure().withErrorCode("INVALID_RESPONSE"));
        }
        return ok(success().withAccountIdentifier("some-id"));
    }
}
