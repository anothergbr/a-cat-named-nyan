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

    public Account fromChangeEvent(SubscriptionEvent changeEvent) {
        Optional<SubscriptionAccount> subscriptionAccount = changeEvent.getPayload().getAccount();
        if (!subscriptionAccount.isPresent()) {
            throw new IllegalArgumentException("Change event does not have a Payload.order object. Not supported");
        }

        Account account = fromEvent(changeEvent);
        account.setId(subscriptionAccount.get().getAccountIdentifier());
        account.setEdition(editionFrom(changeEvent));

        return account;
    }

    private Edition editionFrom(SubscriptionEvent event) {
        Optional<Order> order = event.getPayload().getOrder();
        return order.map(Order::getEditionCode).orElse(null);
    }
}
