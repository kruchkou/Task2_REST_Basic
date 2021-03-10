package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDtoUserMapperTest {

    private static final Integer TEST_ID = 2;
    private static final String TEST_NAME = "Test User name";
    private User user;
    private UserDto userDto;

    @BeforeEach
    public void init() {
        user = new User();
        userDto = new UserDto();

        user.setId(TEST_ID);
        user.setName(TEST_NAME);

        userDto.setId(TEST_ID);
        userDto.setName(TEST_NAME);
    }

    @Test
    public void shouldConvertToEntity() {
        final User testUser = EntityDtoUserMapper.toEntity(userDto);

        assertEquals(TEST_ID, testUser.getId());
        assertEquals(TEST_NAME, testUser.getName());
    }

    @Test
    public void shouldConvertToDto() {
        final UserDto testUser = EntityDtoUserMapper.toDto(user);

        assertEquals(TEST_ID, testUser.getId());
        assertEquals(TEST_NAME, testUser.getName());
    }
}