package com.gbr.nyan.support.builder;

import com.gbr.nyan.appdirect.entity.Flag;
import com.gbr.nyan.appdirect.entity.SubscriptionEvent;

import static com.gbr.nyan.appdirect.entity.Flag.STATELESS;

public class SubscriptionEventBuilder {
    private Flag flag;

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

    public SubscriptionEvent build() {
        SubscriptionEvent subscriptionEvent = new SubscriptionEvent();
        subscriptionEvent.setFlag(flag);
        return subscriptionEvent;
    }
}
