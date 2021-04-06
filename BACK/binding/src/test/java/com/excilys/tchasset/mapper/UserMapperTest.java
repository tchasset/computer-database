package com.excilys.tchasset.mapper;

import com.excilys.tchasset.dto.UserDTO;
import com.excilys.tchasset.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserMapperTest {

    private final User user = new User.Builder()
            .setUsername("A")
            .setPassword("B")
            .setRole("C")
            .setEnabled(true).build();
    private final UserDTO userDTO = new UserDTO.Builder()
            .setUsername("A")
            .setPassword("B")
            .setRole("C")
            .setEnabled(true).build();

    @Test
    public void fromDTO() {
        assertEquals(user, UserMapper.fromDTO(userDTO));
    }

    @Test
    public void toDTO() {
        assertEquals(userDTO, UserMapper.toDTO(user));
    }
}