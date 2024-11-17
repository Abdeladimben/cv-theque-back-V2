package com.api.cv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Register the custom logging interceptor
        restTemplate.getInterceptors().add(new LoggingRequestInterceptor());
        return restTemplate;
    }
}
