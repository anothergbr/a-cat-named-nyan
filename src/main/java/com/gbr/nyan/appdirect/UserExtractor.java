package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserExtractor {
    public User fromEvent(SubscriptionEvent event) {
        return new User();
    }
}
