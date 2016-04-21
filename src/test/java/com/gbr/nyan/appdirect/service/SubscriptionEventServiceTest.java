package com.gbr.nyan.appdirect.service;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import org.junit.Before;
import org.junit.Test;

import static com.gbr.nyan.support.builder.SubscriptionEventBuilder.someStatelessEvent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SubscriptionEventServiceTest {
    private SubscriptionEventService service;

    @Before
    public void thisService() throws Exception {
        service = new SubscriptionEventService();
    }

    @Test
    public void returnsUnknownErrorWhenStatelessEventIsCreated() {
        SubscriptionEvent statelessEvent = someStatelessEvent().build();

        SubscriptionResponse response = service.create(statelessEvent);

        assertThat(response.getSuccess(), is("false"));
        assertThat(response.getErrorCode(), is("UNKNOWN_ERROR"));
    }
}
