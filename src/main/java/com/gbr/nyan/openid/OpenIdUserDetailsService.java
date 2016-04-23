package com.gbr.nyan.openid;

import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * inspired by https://github.com/spring-projects/spring-security/blob/master/samples/xml/openid/src/main/java/org/springframework/security/samples/openid/CustomUserDetailsService.java
 */
@Service
public class OpenIdUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
    private final TokenUserExtractor userExtractor;
    private final UserRepository userRepository;

    @Autowired
    public OpenIdUserDetailsService(TokenUserExtractor userExtractor, UserRepository userRepository) {
        this.userExtractor = userExtractor;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        throw new NotYetImplementedException("Not sure this is needed");
    }

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
        User existingUser = userRepository.findByOpenIdUrl(token.getIdentityUrl());
        if (existingUser != null) {
            return existingUser;
        }
        return saveAndReturnNewUserFrom(token);
    }

    private UserDetails saveAndReturnNewUserFrom(OpenIDAuthenticationToken token) {
        User newUser = userExtractor.fromOpenIdToken(token);
        userRepository.save(newUser);
        return newUser;
    }
}
