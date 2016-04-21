package com.gbr.nyan.appdirect.service;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import org.springframework.stereotype.Service;

import static com.gbr.nyan.appdirect.entity.SubscriptionEvent.Flag.STATELESS;
import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.failure;
import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.success;

@Service
public class SubscriptionEventService {
    public SubscriptionResponse create(SubscriptionEvent someEvent) {
        if (someEvent.getFlag() == STATELESS) {
            return failure().withErrorCode("UNKNOWN_ERROR");
        }

        return success().withAccountIdentifier("some-id");
    }
}
