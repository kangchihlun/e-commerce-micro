package com.reborn.account_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.account_service.dto.LoginRequest;
import com.reborn.account_service.dto.SignupRequest;
import com.reborn.account_service.model.User;
import com.reborn.account_service.service.UserService;
import com.reborn.account_service.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(UserControllerIntegrationTest.TestConfig.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public UserService userService() {
            return mock(UserService.class);
        }
    }

    @Test
    public void testSignup() throws Exception {
        SignupRequest request = TestDataUtil.createSignupRequest();
        User expectedUser = TestDataUtil.createTestUser();

        when(userService.signup(any(SignupRequest.class))).thenReturn(expectedUser);

        mockMvc.perform(post("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedUser.getId()))
                .andExpect(jsonPath("$.email").value(expectedUser.getEmail()))
                .andExpect(jsonPath("$.name").value(expectedUser.getName()));
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest request = TestDataUtil.createLoginRequest();
        User expectedUser = TestDataUtil.createTestUser();

        when(userService.login(any(LoginRequest.class))).thenReturn(expectedUser);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedUser.getId()))
                .andExpect(jsonPath("$.email").value(expectedUser.getEmail()))
                .andExpect(jsonPath("$.name").value(expectedUser.getName()));
    }
} 