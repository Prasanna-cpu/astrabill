package com.spring.astrabill.backend.service.implementation;

import com.spring.astrabill.backend.config.JWTProvider;
import com.spring.astrabill.backend.config.JWTValidator;
import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.entity.User;
import com.spring.astrabill.backend.exceptions.ForbiddenActivityException;
import com.spring.astrabill.backend.exceptions.ObjectNotFoundException;
import com.spring.astrabill.backend.mapper.UserMapper;
import com.spring.astrabill.backend.repository.UserRepository;
import com.spring.astrabill.backend.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ObjectNotFoundException.class})
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.
                findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User with email " + email + " not found"));
        UserDTO userDTO = UserMapper.mapToUserDTO(user);
        return userDTO;
    }

    @Override
    public UserDTO getUserByPhone(String phone) {
        User user = userRepository.
                findByPhone(phone)
                .orElseThrow(() -> new ObjectNotFoundException("User with phone " + phone + " not found"));
        UserDTO userDTO = UserMapper.mapToUserDTO(user);
        return userDTO;
    }

    @Override
    public UserDTO getUserByAccessToken(String accessToken) {
        String email = jwtProvider.getEmailFromToken(accessToken);
        return getUserByEmail(email);
    }

    @Override
    public UserDTO getCurrentAuthenticatedUser() {
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            throw new ForbiddenActivityException("User is not authenticated");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }

    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository.
                findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found"));
        UserDTO userDTO = UserMapper.mapToUserDTO(user);
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream().map(UserMapper::mapToUserDTO).toList();
        return userDTOs;
    }
}
