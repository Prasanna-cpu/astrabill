package com.spring.astrabill.backend.controller;

import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.request.LoginRequest;
import com.spring.astrabill.backend.response.ApiResponse;
import com.spring.astrabill.backend.response.AuthResponse;
import com.spring.astrabill.backend.response.LoginResponse;
import com.spring.astrabill.backend.response.RegisterResponse;
import com.spring.astrabill.backend.service.abstraction.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO){
        RegisterResponse response = authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AuthResponse(
                        response.getAccessToken(),
                        response.getRefreshToken(),
                        response.getData(),
                        "User registered successfully",
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new AuthResponse(
                        response.getAccessToken(),
                        response.getRefreshToken(),
                        null,
                        "User logged in successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }



}
