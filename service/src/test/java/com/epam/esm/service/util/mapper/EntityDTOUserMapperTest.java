package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDTOUserMapperTest {

    private static final Integer TEST_ID = 2;
    private static final String TEST_NAME = "Test User name";
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void init() {
        user = new User();
        userDTO = new UserDTO();

        user.setId(TEST_ID);
        user.setName(TEST_NAME);

        userDTO.setId(TEST_ID);
        userDTO.setName(TEST_NAME);
    }

    @Test
    public void shouldConvertToEntity() {
        final User testUser = EntityDTOUserMapper.toEntity(userDTO);

        assertEquals(TEST_ID, testUser.getId());
        assertEquals(TEST_NAME, testUser.getName());
    }

    @Test
    public void shouldConvertToDTO() {
        final UserDTO testUser = EntityDTOUserMapper.toDTO(user);

        assertEquals(TEST_ID, testUser.getId());
        assertEquals(TEST_NAME, testUser.getName());
    }
}