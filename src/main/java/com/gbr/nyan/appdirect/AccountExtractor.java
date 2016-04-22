package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.domain.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountExtractor {
    public Account fromEvent(SubscriptionEvent event) {
        return new Account("some-id");
    }
}
