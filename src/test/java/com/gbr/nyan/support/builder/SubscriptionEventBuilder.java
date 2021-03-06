package com.gbr.nyan.support.builder;

import com.gbr.nyan.appdirect.entity.*;
import com.gbr.nyan.domain.Account.Edition;

import static com.gbr.nyan.appdirect.entity.Flag.STATELESS;
import static com.gbr.nyan.domain.Account.Edition.BASIC;

public class SubscriptionEventBuilder {
    private Flag flag;
    private Edition edition = BASIC;
    private EventUser creator;
    private String accountIdentifier;

    public static SubscriptionEventBuilder someEvent() {
        return new SubscriptionEventBuilder();
    }

    public static SubscriptionEventBuilder someStatelessEvent() {
        return someEvent().withFlag(STATELESS);
    }

    public SubscriptionEventBuilder withFlag(Flag flag) {
        this.flag = flag;
        return this;
    }

    public SubscriptionEventBuilder withEdition(Edition edition) {
        this.edition = edition;
        return this;
    }

    public SubscriptionEventBuilder withAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
        return this;
    }

    public SubscriptionEventBuilder withCreator(SubscriptionUserBuilder userBuilder) {
        this.creator = userBuilder.build();
        return this;
    }

    public SubscriptionEvent build() {
        SubscriptionEvent subscriptionEvent = new SubscriptionEvent();
        subscriptionEvent.setCreator(creator);
        subscriptionEvent.setFlag(flag);
        subscriptionEvent.setPayload(buildPayload());

        return subscriptionEvent;
    }

    private Payload buildPayload() {
        Payload payload = new Payload();
        payload.setOrder(buildOrder());
        payload.setAccount(buildAccount());

        return payload;
    }

    private Order buildOrder() {
        Order order = new Order();
        order.setEditionCode(this.edition);

        return order;
    }

    private SubscriptionAccount buildAccount() {
        SubscriptionAccount account = new SubscriptionAccount();
        account.setAccountIdentifier(accountIdentifier);

        return account;
    }
}
