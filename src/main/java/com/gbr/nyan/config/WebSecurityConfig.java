package com.gbr.nyan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        noSecurityAtAll(http);
        noCsrfNeither(http);
        allowFrames(http);
    }

    private void allowFrames(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
    }

    private void noCsrfNeither(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    private void noSecurityAtAll(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
    }
}
