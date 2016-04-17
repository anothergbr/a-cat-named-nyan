package com.gbr.nyan.config;

import com.gbr.nyan.support.HandlebarsRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public HandlebarsRenderer handlebarsRenderer() {
        return HandlebarsRenderer.aRenderer();
    }
}
