package com.gbr.nyan.config;

import com.gbr.nyan.support.FullRequestLogFilter;
import com.gbr.nyan.support.HandlebarsRenderer;
import com.gbr.nyan.support.HttpClient;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.gbr.nyan.support.HttpClient.aClientAcceptingOnlyJson;
import static javax.servlet.DispatcherType.REQUEST;

@Configuration
public class BeansConfig {
    @Bean
    public FilterRegistrationBean registerFullRequestLogger() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(REQUEST);
        registration.setFilter(new FullRequestLogFilter());

        return registration;
    }

    @Bean
    public HandlebarsRenderer handlebarsRenderer() {
        return HandlebarsRenderer.aRenderer();
    }

    @Bean
    public HttpClient jsonHttpClient() {
        return aClientAcceptingOnlyJson();
    }
}
