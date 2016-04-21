package com.gbr.nyan.appdirect.entity;

public class SubscriptionEvent {
    private Flag flag;

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public enum Flag {
        STATELESS, DEVELOPMENT
    }
}
