package com.reborn.client_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {
    
    @Value("${account.service.url}")
    private String accountServiceUrl;
    
    @Value("${product.service.url}")
    private String productServiceUrl;
    
    @Value("${order.service.url}")
    private String orderServiceUrl;
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    public String getAccountServiceUrl() {
        return accountServiceUrl;
    }
    
    public String getProductServiceUrl() {
        return productServiceUrl;
    }
    
    public String getOrderServiceUrl() {
        return orderServiceUrl;
    }
} 