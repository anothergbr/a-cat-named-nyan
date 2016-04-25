package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.Order;
import com.gbr.nyan.appdirect.entity.SubscriptionAccount;
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

    public Account whenAccountExists(SubscriptionEvent event) {
        Optional<SubscriptionAccount> subscriptionAccount = event.getPayload().getAccount();
        if (!subscriptionAccount.isPresent()) {
            throw new IllegalArgumentException("Event does not have a Payload.order object. The account is assumed to exist.");
        }

        Account account = fromEvent(event);
        account.setId(subscriptionAccount.get().getAccountIdentifier());
        account.setEdition(editionFrom(event));

        return account;
    }

    private Edition editionFrom(SubscriptionEvent event) {
        Optional<Order> order = event.getPayload().getOrder();
        return order.map(Order::getEditionCode).orElse(null);
    }
}
