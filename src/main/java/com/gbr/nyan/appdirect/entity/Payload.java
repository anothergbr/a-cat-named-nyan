package com.gbr.nyan.appdirect.entity;

import java.util.Optional;

public class Payload {
    private EventUser user;
    private Company company;
    private Order order;

    public EventUser getUser() {
        return user;
    }

    public Company getCompany() {
        return company;
    }

    public Optional<Order> getOrder() {
        return Optional.ofNullable(order);
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
