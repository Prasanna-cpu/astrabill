package com.spring.astrabill.backend.service.abstraction;

import com.spring.astrabill.backend.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserByEmail(String email);
    UserDTO getUserByPhone(String phone);
    UserDTO getUserByAccessToken(String accessToken);
    UserDTO getCurrentAuthenticatedUser();
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
}
