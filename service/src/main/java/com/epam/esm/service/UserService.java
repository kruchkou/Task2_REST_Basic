package com.epam.esm.service;

import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.model.dto.UserDto;

import java.util.List;

/**
 * Interface provides methods to interact with UserDao.
 * Methods should transforms received information into Dao-accepted data and invoke corresponding methods.
 */
public interface UserService {

    /**
     * Invokes Dao method to get User with provided id.
     *
     * @param id is id of user to be returned.
     * @return {@link UserDto} object with user data.
     */
    UserDto getUser(int id);

    /**
     * Invokes Dao method to get List of all Users from database.
     *
     * @param page is {@link Page} object with page number and page size
     * @return List of {@link UserDto} objects with user data.
     */
    List<UserDto> getUsers(Page page);

}
