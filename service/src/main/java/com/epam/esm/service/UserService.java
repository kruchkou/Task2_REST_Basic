package com.epam.esm.service;

import com.epam.esm.service.model.dto.UserDTO;

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
     * @return {@link UserDTO} object with user data.
     */
    UserDTO getUser(int id);

    /**
     * Invokes DAO method to get List of all Users from database.
     *
     * @return List of {@link UserDTO} objects with user data.
     */
    List<UserDTO> getUsers();

}
