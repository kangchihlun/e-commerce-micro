package com.reborn.account_service.dto;

import com.reborn.account_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {
    private boolean success;
    private String message;
    private User user;

    public static SignupResponse success(User user) {
        return new SignupResponse(true, "User registered successfully", user);
    }

    public static SignupResponse error(String message) {
        return new SignupResponse(false, message, null);
    }
} 