package com.spring.astrabill.backend.service.implementation;

import com.spring.astrabill.backend.config.JWTProvider;
import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.entity.User;
import com.spring.astrabill.backend.enums.UserRoles;
import com.spring.astrabill.backend.exceptions.ConflictingResourcesException;
import com.spring.astrabill.backend.exceptions.ForbiddenActivityException;
import com.spring.astrabill.backend.exceptions.ObjectNotFoundException;
import com.spring.astrabill.backend.mapper.UserMapper;
import com.spring.astrabill.backend.repository.UserRepository;
import com.spring.astrabill.backend.request.LoginRequest;
import com.spring.astrabill.backend.response.AuthResponse;
import com.spring.astrabill.backend.response.LoginResponse;
import com.spring.astrabill.backend.response.RegisterResponse;
import com.spring.astrabill.backend.service.abstraction.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, RuntimeException.class, ForbiddenActivityException.class, ConflictingResourcesException.class, ObjectNotFoundException.class})
@Slf4j
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsServiceImplementation customUserDetailsService;
    private final JWTProvider jwtProvider;

    private Authentication authenticateUser(String email, String password){
        UserDetails details=customUserDetailsService.loadUserByUsername(email);

//        if(details == null){
//            throw  new BadCredentialsException("Invalid credentials");
//        }

        if(!passwordEncoder.matches(password,details.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    @Override
    public RegisterResponse register(UserDTO userDTO) {

        Boolean isUserExists = userRepository.existsByEmail(userDTO.getEmail());
        if (isUserExists) {
            throw new ConflictingResourcesException("User with email " + userDTO.getEmail() + " already exists");
        }

        if(userDTO.getRole().equals(UserRoles.ROLE_ADMIN)){
            throw new ForbiddenActivityException("Admin user cannot be registered");
        }

        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPhone(userDTO.getPhone());
        newUser.setFullName(userDTO.getFullName());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRole(userDTO.getRole());
        newUser.setLastLogin(LocalDateTime.now());

        User savedUser = userRepository.save(newUser);
        UserDTO savedUserDTO = UserMapper.mapToUserDTO(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                savedUserDTO.getEmail(),
                savedUserDTO.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        RegisterResponse response = new RegisterResponse(
                accessToken,
                refreshToken,
                savedUserDTO
        );

        return response;

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Boolean isUserExists = userRepository.existsByEmail(loginRequest.getEmail());
        if(!isUserExists){
            throw new ForbiddenActivityException("User with email " + loginRequest.getEmail() + " does not exist");
        }

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticateUser(email, password);

        String accessToken = jwtProvider.generateToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        LoginResponse response = new LoginResponse(accessToken, refreshToken);

        return response;

    }
}
