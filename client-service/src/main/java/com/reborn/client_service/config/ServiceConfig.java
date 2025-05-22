package com.reborn.client_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    
    @Value("${account.service.url}")
    private String accountServiceUrl;
    
    @Value("${product.service.url}")
    private String productServiceUrl;
    
    public String getAccountServiceUrl() {
        return accountServiceUrl;
    }
    
    public String getProductServiceUrl() {
        return productServiceUrl;
    }
} 