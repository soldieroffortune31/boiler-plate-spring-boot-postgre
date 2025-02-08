package com.example.springpostgres.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${base_url_fake_api}")
    private String baseUrlFakeAPI;

    @Bean
    public WebClient fakeApiClient(){
        return WebClient.builder()
            .baseUrl(baseUrlFakeAPI)
            .build();
    }

}