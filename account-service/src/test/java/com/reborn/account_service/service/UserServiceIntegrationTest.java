package com.reborn.account_service.service;

import com.reborn.account_service.dto.LoginRequest;
import com.reborn.account_service.dto.SignupRequest;
import com.reborn.account_service.dto.SignupResponse;
import com.reborn.account_service.model.User;
import com.reborn.account_service.repository.UserRepository;
import com.reborn.account_service.util.TestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void testSignup_Success() {
        // Given
        SignupRequest request = TestDataUtil.createSignupRequest();

        // When
        SignupResponse response = userService.signup(request);

        // Then
        assertTrue(response.isSuccess());
        assertEquals("User registered successfully", response.getMessage());
        assertNotNull(response.getUser());
        assertNotNull(response.getUser().getId());
        assertEquals(request.getEmail(), response.getUser().getEmail());
        
        // Verify user is saved in database
        User savedUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        assertNotNull(savedUser);
        assertEquals(response.getUser().getId(), savedUser.getId());
    }

    @Test
    public void testSignup_UserExists() {
        // Given
        SignupRequest request = TestDataUtil.createSignupRequest();
        userService.signup(request); // First signup

        // When
        SignupResponse response = userService.signup(request); // Second signup with same email

        // Then
        assertFalse(response.isSuccess());
        assertEquals("Email already exists", response.getMessage());
        assertNull(response.getUser());
    }

    @Test
    public void testLogin() {
        // Given
        SignupRequest signupRequest = TestDataUtil.createSignupRequest();
        SignupResponse signupResponse = userService.signup(signupRequest);
        User createdUser = signupResponse.getUser();
        
        LoginRequest loginRequest = TestDataUtil.createLoginRequest();

        // When
        User loggedInUser = userService.login(loginRequest);

        // Then
        assertNotNull(loggedInUser);
        assertEquals(createdUser.getId(), loggedInUser.getId());
        assertEquals(createdUser.getEmail(), loggedInUser.getEmail());
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Given
        LoginRequest loginRequest = TestDataUtil.createLoginRequest();
        loginRequest.setPassword("wrongpassword");

        // When/Then
        assertThrows(RuntimeException.class, () -> {
            userService.login(loginRequest);
        });
    }
} 