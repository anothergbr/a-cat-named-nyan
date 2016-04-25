package com.gbr.nyan.appdirect.entity;

import java.util.Optional;

public class SubscriptionEvent {
    private Flag flag;
    private EventUser creator;
    private Payload payload;

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public Optional<EventUser> getCreator() {
        return Optional.ofNullable(creator);
    }

    public void setCreator(EventUser creator) {
        this.creator = creator;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
