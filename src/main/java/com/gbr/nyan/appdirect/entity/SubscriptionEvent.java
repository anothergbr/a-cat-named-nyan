package com.gbr.nyan.appdirect.entity;

public class SubscriptionEvent {
    private Flag flag;
    private User creator;
    private Payload payload;

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public User getCreator() {
        return creator;
    }

    public Payload getPayload() {
        return payload;
    }
}
