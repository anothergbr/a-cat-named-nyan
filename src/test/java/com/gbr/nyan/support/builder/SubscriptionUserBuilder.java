package com.gbr.nyan.support.builder;

import com.gbr.nyan.appdirect.entity.EventUser;

public class SubscriptionUserBuilder {
    private String email;
    private String uuid;
    private String firstName;
    private String lastName;
    private String openId;

    public static SubscriptionUserBuilder someUser() {
        return new SubscriptionUserBuilder();
    }

    public EventUser build() {
        EventUser user = new EventUser();
        user.setEmail(email);
        user.setUuid(uuid);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setOpenId(openId);

        return user;
    }

    public SubscriptionUserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public SubscriptionUserBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public SubscriptionUserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public SubscriptionUserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SubscriptionUserBuilder withOpenId(String openId) {
        this.openId = openId;
        return this;
    }
}
