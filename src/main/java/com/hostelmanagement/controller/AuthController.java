package com.hostelmanagement.controller;

import com.hostelmanagement.dto.LoginRequest;
import com.hostelmanagement.dto.RegisterRequest;
import com.hostelmanagement.service.AuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationService
            authenticationService;

    public AuthController(
            AuthenticationService authenticationService
    ) {

        this.authenticationService =
                authenticationService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {

        String response =
                authenticationService
                        .register(request);

        return ResponseEntity.ok(
                Map.of("message", response)
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {

        return ResponseEntity.ok(
                authenticationService.login(request)
        );
    }
}