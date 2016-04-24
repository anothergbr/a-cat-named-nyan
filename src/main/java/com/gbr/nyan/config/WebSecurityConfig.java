package com.gbr.nyan.config;

import com.gbr.nyan.openid.OpenIdUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    OpenIdUserDetailsService openIdUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        noSecurityAtAll(http);
        noCsrfNeither(http);
        allowFrames(http);
        enableOpenIdLogin(http);
    }

    private void enableOpenIdLogin(HttpSecurity http) throws Exception {
        http.openidLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .authenticationUserDetailsService(openIdUserDetailsService)
                .attributeExchange(".*.byappdirect.com.*")
                .attribute("uuid").type("https://www.appdirect.com/schema/user/uuid").required(true)
                .and()
                .attribute("email").type("http://axschema.org/contact/email").required(true)
                .and()
                .attribute("fullname").type("http://axschema.org/namePerson").required(true);
        http.logout().logoutSuccessUrl("/login?logout=true");
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
