package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import org.junit.Before;
import org.junit.Test;

import static com.gbr.nyan.appdirect.entity.Flag.DEVELOPMENT;
import static com.gbr.nyan.domain.Account.Edition.BASIC;
import static com.gbr.nyan.support.ContentOf.resourceAsString;
import static com.gbr.nyan.support.JacksonHelper.objectMapperConfiguredLikeSpringDoes;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class SubscriptionEventParserTest {
    private SubscriptionEventParser parser;

    @Before
    public void thisParser() throws Exception {
        parser = new SubscriptionEventParser(objectMapperConfiguredLikeSpringDoes());
    }

    @Test
    public void canReadCompleteSubscriptionOrder() throws Exception {
        String json = resourceAsString("events/subscription-order-development.json");
        SubscriptionEvent subscriptionEvent = parser.fromJson(json);

        assertThat(subscriptionEvent.getFlag(), is(DEVELOPMENT));
        assertThat(subscriptionEvent.getCreator().getUuid(), is("dac5f67b-7da7-4603-bb1b-3fd507509081"));
        assertThat(subscriptionEvent.getCreator().getOpenId(), is("https://gabrielspub-test.byappdirect.com/openid/id/dac5f67b-7da7-4603-bb1b-3fd507509081"));
        assertThat(subscriptionEvent.getCreator().getEmail(), is("gabriel.x@gmail.com"));
        assertThat(subscriptionEvent.getCreator().getFirstName(), is("Gabriel"));
        assertThat(subscriptionEvent.getCreator().getLastName(), is("SomeLastName"));
        assertThat(subscriptionEvent.getPayload().getCompany().getName(), is("Gabriel's Company"));
        assertThat(subscriptionEvent.getPayload().getOrder().getEditionCode(), is(BASIC));
    }

    @Test
    public void supportsMissingProperties() throws Exception {
        String json = resourceAsString("events/subscription-order-development.json");
        SubscriptionEvent subscriptionEvent = parser.fromJson(json);

        assertThat(subscriptionEvent.getPayload().getUser(), is(nullValue()));
    }
}