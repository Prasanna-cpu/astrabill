package com.spring.astrabill.backend.mapper;

import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.entity.User;

public class UserMapper {
    public static UserDTO mapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setFullName(user.getFullName());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setVerified(user.getVerified());


        return userDTO;
    }

    public static User mapToUser(UserDTO userDTO){
        User user = new User();

        // user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setFullName(userDTO.getFullName());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setLastLogin(userDTO.getLastLogin());
        user.setVerified(userDTO.getVerified());

        return user;
    }

}
