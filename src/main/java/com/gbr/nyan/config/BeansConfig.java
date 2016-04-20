package com.gbr.nyan.config;

import com.gbr.nyan.support.HandlebarsRenderer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.gbr.nyan.support.FullRequestLogFilter.aFullRequestLogFilter;

@Configuration
public class BeansConfig {
    @Bean
    public FilterRegistrationBean logFullRequests() {
        return aFullRequestLogFilter();
    }

    @Bean
    public HandlebarsRenderer handlebarsRenderer() {
        return HandlebarsRenderer.aRenderer();
    }
}
