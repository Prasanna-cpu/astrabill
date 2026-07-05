package com.spring.astrabill.backend.controller;

import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.response.ApiResponse;
import com.spring.astrabill.backend.service.abstraction.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/email/{email}")
    public ResponseEntity<ApiResponse> getUserByEmailHandler(@PathVariable String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        userDTO,
                        "User fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/user/phone/{phone}")
    public ResponseEntity<ApiResponse> getUserByPhoneHandler(@PathVariable String phone) {
        UserDTO userDTO = userService.getUserByPhone(phone);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        userDTO,
                        "User fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/user/access-token")
    public ResponseEntity<ApiResponse> getUserByAccessTokenHandler(@RequestHeader("Authorization") String jwt){
        UserDTO userDTO = userService.getUserByAccessToken(jwt);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        userDTO,
                        "User fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/user/current")
    public ResponseEntity<ApiResponse> getCurrentAuthenticatedUserHandler(){
        UserDTO userDTO = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        userDTO,
                        "User fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserByIdHandler(@PathVariable String id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        userDTO,
                        "User fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/all-users")
    public ResponseEntity<ApiResponse> getAllUsersHandler(){
        List<UserDTO> userDTOList = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        userDTOList,
                        "Users fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }
}

