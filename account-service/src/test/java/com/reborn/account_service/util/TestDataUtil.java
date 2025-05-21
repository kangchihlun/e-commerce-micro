package com.reborn.account_service.util;

import com.reborn.account_service.dto.LoginRequest;
import com.reborn.account_service.dto.SignupRequest;
import com.reborn.account_service.model.User;

public class TestDataUtil {
    
    public static SignupRequest createSignupRequest() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        return request;
    }

    public static LoginRequest createLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        return request;
    }

    public static User createTestUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        return user;
    }
} 