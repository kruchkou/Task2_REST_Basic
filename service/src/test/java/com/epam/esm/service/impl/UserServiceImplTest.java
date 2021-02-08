package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.UserDAO;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.exception.impl.UserNotFoundException;
import com.epam.esm.service.model.dto.UserDto;
import com.epam.esm.service.util.mapper.EntityDtoUserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final int TEST_ID = 1;
    private static final String TEST_NAME = "User";

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDAO userDAO;

    private User user;

    private List<User> userList;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(TEST_ID);
        user.setName(TEST_NAME);

        userList = new ArrayList<>();
        userList.add(user);

        user = new User();
        user.setId(TEST_ID);
        user.setName(TEST_NAME);

        userService = new UserServiceImpl(userDAO);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getUserByID() {
        given(userDAO.getUser(TEST_ID)).willReturn(Optional.of(user));
        UserDto receivedUserDTO = userService.getUser(TEST_ID);

        UserDto testedDTO = EntityDtoUserMapper.toDTO(user);
        assertEquals(testedDTO, receivedUserDTO);
    }


    @Test
    public void getUserByIDShouldException() {
        given(userDAO.getUser(TEST_ID)).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(TEST_ID));
    }

    @Test
    public void getUsers() {
        given(userDAO.getUsers()).willReturn(userList);

        List<UserDto> receivedDTOList = userService.getUsers();
        List<UserDto> testDTOList = EntityDtoUserMapper.toDTO(userList);

        assertIterableEquals(testDTOList, receivedDTOList);
    }

}