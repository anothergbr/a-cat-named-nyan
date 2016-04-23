package com.gbr.nyan.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Dummy abstract class to encapsulate the hardcoded and not relevant stuff that UserDetails require.
 * Stuff like: hardcoded authorities, account and credentials expiration, a password (using AppDirect as an SSO, so no need), etc.
 */
public abstract class UserBasicHardcodedSpringSecurityStuff implements UserDetails {
    private final static long serialVersionUID = -1;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return "there-is-no-password";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
