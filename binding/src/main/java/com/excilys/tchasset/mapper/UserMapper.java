package com.excilys.tchasset.mapper;

import com.excilys.tchasset.dto.UserDTO;
import com.excilys.tchasset.model.User;

public class UserMapper {

    public static User fromDTO(UserDTO userDTO) {
        return new User.Builder()
                .setUsername(userDTO.getUsername())
                .setPassword(userDTO.getPassword())
                .setRole(userDTO.getRole())
                .setEnabled(true)
                .build();
    }


    public static UserDTO toDTO(User user) {

        return new UserDTO.Builder()
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setRole(user.getRole())
                .build();
    }
}
