package com.minicloud.mapper;

import com.minicloud.dto.UserDTO;
import com.minicloud.model.User;

public class UserMapper {

    // Convertir Model a DTO
    public static UserDTO toDTO(User user) {
       if (user == null) {
           return null;
       }
       return new UserDTO(
              user.getId(),
              user.getUserName(),
              user.getEmail()
       );

    }

    // Convertir DTO a Model
    public static User toModel(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }      
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
