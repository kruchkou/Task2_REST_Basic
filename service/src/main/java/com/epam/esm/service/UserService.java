package com.epam.esm.service;

import com.epam.esm.service.model.dto.UserDto;

import java.util.List;

/**
 * Interface provides methods to interact with UserDAO.
 * Methods should transforms received information into DAO-accepted data and invoke corresponding methods.
 */
public interface UserService {

    /**
     * Invokes DAO method to get User with provided id.
     *
     * @param id is id of user to be returned.
     * @return {@link UserDto} object with user data.
     */
    UserDto getUser(int id);

    /**
     * Invokes DAO method to get List of all Users from database.
     *
     * @return List of {@link UserDto} objects with user data.
     */
    List<UserDto> getUsers();

}
