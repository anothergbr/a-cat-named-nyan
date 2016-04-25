package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.Order;
import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.Account.Edition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountExtractor {
    public Account fromEvent(SubscriptionEvent event) {
        Account account = new Account();
        account.setEdition(editionFrom(event));
        return account;
    }

    private Edition editionFrom(SubscriptionEvent event) {
        Optional<Order> order = event.getPayload().getOrder();
        return order.map(Order::getEditionCode).orElse(null);
    }
}
