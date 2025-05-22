package com.reborn.account_service.controller;

import com.reborn.account_service.dto.LoginRequest;
import com.reborn.account_service.dto.SignupRequest;
import com.reborn.account_service.dto.SignupResponse;
import com.reborn.account_service.model.User;
import com.reborn.account_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User user = userService.login(request);
        return ResponseEntity.ok(user);
    }
} 