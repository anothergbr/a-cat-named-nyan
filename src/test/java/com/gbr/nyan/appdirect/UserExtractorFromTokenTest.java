package com.gbr.nyan.appdirect;

import com.gbr.nyan.domain.User;
import org.junit.Test;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.security.openid.OpenIDAuthenticationStatus.SUCCESS;

public class UserExtractorFromTokenTest {
    private UserExtractor extractor = new UserExtractor();

    @Test
    public void extractsUserFromFullToken() {
        OpenIDAuthenticationToken fullToken = someToken("the-url", "the-email", "the-uuid", "the-fullname");

        User user = extractor.fromOpenIdToken(fullToken);

        assertThat(user.getOpenIdUrl(), is("the-url"));
        assertThat(user.getEmail(), is("the-email"));
        assertThat(user.getUuid(), is("the-uuid"));
        assertThat(user.getFullName(), is("the-fullname"));
    }

    private OpenIDAuthenticationToken someToken(String openIdUrl, String email, String uuid, String fullName) {
        return new OpenIDAuthenticationToken(SUCCESS, openIdUrl, "some-message", asList(
                anAttribute("email", email),
                anAttribute("uuid", uuid),
                anAttribute("fullname", fullName)));
    }

    private OpenIDAttribute anAttribute(String name, String value) {
        return new OpenIDAttribute(name, "some-type", singletonList(value));
    }
}