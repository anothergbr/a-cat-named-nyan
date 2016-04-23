package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.domain.User;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserExtractor {
    public User fromEvent(SubscriptionEvent event) {
        // TODO: fill this
        User user = new User();
        user.setEmail("some@email.com");
        return user;
    }

    public User fromOpenIdToken(OpenIDAuthenticationToken token) {
        String email = null;
        String uuid = null;
        String fullName = null;

        List<OpenIDAttribute> attributes = token.getAttributes();
        for (OpenIDAttribute attribute : attributes) {
            String attributeName = attribute.getName();
            String attributeValue = attribute.getValues().get(0);

            switch (attributeName) {
                case "uuid":
                    uuid = attributeValue;
                    break;
                case "email":
                    email = attributeValue;
                    break;
                case "fullname":
                    fullName = attributeValue;
                    break;
            }
        }

        User user = new User();
        user.setEmail(email);
        user.setOpenIdUrl(token.getIdentityUrl());
        user.setUuid(uuid);
        user.setFullName(fullName);

        return user;
    }
}
