package com.gbr.nyan.appdirect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SubscriptionEventParser {
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public SubscriptionEventParser(ObjectMapper jacksonOjectMapper) {
        this.jacksonObjectMapper = jacksonOjectMapper;
    }

    public SubscriptionEvent fromJson(String rawCreateEvent) throws IOException {
        return jacksonObjectMapper.readValue(rawCreateEvent, SubscriptionEvent.class);
    }
}
