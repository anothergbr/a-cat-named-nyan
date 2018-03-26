package com.gbr.nyan.config;

import com.gbr.nyan.support.HandlebarsRenderer;
import com.gbr.nyan.web.support.FullRequestLogFilter;
import com.gbr.nyan.web.support.OauthHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static javax.servlet.DispatcherType.REQUEST;

@Configuration
public class BeansConfig {
    @Bean
    public FilterRegistrationBean<FullRequestLogFilter> registerFullRequestLogger() {
        FilterRegistrationBean<FullRequestLogFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(REQUEST);
        registration.setFilter(new FullRequestLogFilter());

        return registration;
    }

    @Bean
    public OauthHttpClient oauthHttpClient(@Value("${oauth.appdirect.consumer.key}") String oauthConsumerKey, @Value("${oauth.appdirect.consumer.secret}") String oauthConsumerSecret) {
        return new OauthHttpClient(oauthConsumerKey, oauthConsumerSecret);
    }

    @Bean
    public HandlebarsRenderer handlebarsRenderer() {
        return HandlebarsRenderer.aRenderer();
    }
}
