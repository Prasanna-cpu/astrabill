package com.spring.astrabill.backend.service.abstraction;

import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.request.LoginRequest;
import com.spring.astrabill.backend.response.AuthResponse;
import com.spring.astrabill.backend.response.LoginResponse;
import com.spring.astrabill.backend.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(UserDTO userDTO);
    LoginResponse login(LoginRequest loginRequest);
}
