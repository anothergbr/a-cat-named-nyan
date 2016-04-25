package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.EventUser;
import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.domain.User;
import org.springframework.stereotype.Component;

@Component
public class EventUserExtractor {
    public User fromCreationEvent(SubscriptionEvent event) {
        if (!event.getCreator().isPresent()) {
            throw new IllegalArgumentException("Your creation event does not have a creator. This is not supported.");
        }
        EventUser creator = event.getCreator().get();

        User user = new User();
        user.setEmail(creator.getEmail());
        user.setFullName(buildFullName(creator));
        user.setOpenIdUrl(creator.getOpenId());
        user.setUuid(creator.getUuid());

        return user;
    }

    private String buildFullName(EventUser creator) {
        String fullName = blankIfNull(creator.getFirstName());
        String lastName = blankIfNull(creator.getLastName());
        boolean shouldAddMiddleSpace = !fullName.equals("") && !lastName.equals("");

        return fullName + (shouldAddMiddleSpace ? " " : "") + lastName;
    }

    private String blankIfNull(String str) {
        return str == null ? "" : str;
    }
}
