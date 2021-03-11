package com.epam.esm.service;

import com.epam.esm.service.model.dto.UserDto;
import com.epam.esm.service.model.util.SignUpUserData;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface provides methods to interact with UserRepository.
 * Methods should transforms received information into Repository-accepted data and invoke corresponding methods.
 */
public interface UserService {

    /**
     * Invokes Repository method to get User with provided id.
     *
     * @param id is id of user to be returned.
     * @return {@link UserDto} object with user data.
     */
    UserDto getUser(int id);

    /**
     * Invokes Repository method to get List of all Users from database.
     *
     * @param pageable is {@link Pageable} object with page number and page size
     * @return List of {@link UserDto} objects with user data.
     */
    List<UserDto> getUsers(Pageable pageable);

    /**
     * Invokes Repository method to sign up user
     *
     * @param signUpUserData is {@link SignUpUserData} object with user data for sign up
     * @return {@link UserDto} object with user data.
     */
    UserDto signUp(SignUpUserData signUpUserData);

}
