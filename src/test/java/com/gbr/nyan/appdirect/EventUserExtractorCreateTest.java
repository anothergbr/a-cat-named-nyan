package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.domain.User;
import com.gbr.nyan.support.builder.SubscriptionUserBuilder;
import org.junit.Test;

import static com.gbr.nyan.support.builder.SubscriptionEventBuilder.someEvent;
import static com.gbr.nyan.support.builder.SubscriptionUserBuilder.someUser;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class EventUserExtractorCreateTest {
    private final EventUserExtractor extractor = new EventUserExtractor();

    @Test
    public void doesNotSetAccount() {
        User user = extractor.fromCreationEvent(eventWith(someUser()));
        assertThat(user.getAccount(), is(nullValue()));
    }

    @Test
    public void readsUserFromTheCreatorFieldsOfTheEvent() {
        SubscriptionEvent anEvent = eventWith(someUser()
                .withEmail("g@x.com")
                .withUuid("1234")
                .withFirstName("First")
                .withLastName("Last")
                .withOpenId("some-url"));

        User user = extractor.fromCreationEvent(anEvent);

        assertThat(user.getEmail(), is("g@x.com"));
        assertThat(user.getUuid(), is("1234"));
        assertThat(user.getFullName(), is("First Last"));
        assertThat(user.getOpenIdUrl(), is("some-url"));
    }

    @Test
    public void doesNotAddTrailingSpaceToFullName() {
        User user = extractor.fromCreationEvent(eventWith(someUser()
                .withFirstName("Just the first")));

        assertThat(user.getFullName(), is("Just the first"));
    }

    @Test
    public void doesNotAddLeadingSpaceToFullName() {
        User user = extractor.fromCreationEvent(eventWith(someUser()
                .withLastName("Just a last name")));

        assertThat(user.getFullName(), is("Just a last name"));
    }

    private SubscriptionEvent eventWith(SubscriptionUserBuilder userBuilder) {
        return someEvent().withCreator(userBuilder).build();
    }
}
