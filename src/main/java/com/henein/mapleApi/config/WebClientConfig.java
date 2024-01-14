package com.henein.mapleApi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${nexon.access-key}")
    private String NEXON_ACCESS_KEY;
    @Bean
    public WebClient webClient() {
        return WebClient.builder()

                .baseUrl("https://open.api.nexon.com/maplestory/v1/") // 기본 URL 설정
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8") // 기본 헤더 설정
                .defaultHeader("x-nxopen-api-key", NEXON_ACCESS_KEY)
                .build();


    }
}
