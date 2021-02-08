package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.UserDAO;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.impl.UserNotFoundException;
import com.epam.esm.service.model.dto.UserDto;
import com.epam.esm.service.util.mapper.EntityDtoUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link UserService}. Interface provides methods to interact with UserDAO.
 * Methods should transforms received information into DAO-accepted data and invoke corresponding methods.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Format string to provide info by what id User wasn't found.
     */
    private static final String NOT_FOUND_BY_ID_PARAMETER = "id: %d";

    /**
     * Error message when User wasn't found by id
     */
    private static final String NO_USER_WITH_ID_FOUND = "No user with id: %d found";

    /**
     * Error code when User wasn't found by id
     */
    private static final String ERROR_CODE_USER_BY_ID_NOT_FOUND_FAILED = "0302404%d";

    /**
     * An object of {@link UserDAO}
     */
    private final UserDAO userDAO;

    /**
     * Public constructor that receives userDAO
     *
     * @param userDAO is {@link UserDAO} interface providing DAO methods.
     */
    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Invokes DAO method to get User with provided id.
     *
     * @param id is id of user to be returned.
     * @return {@link UserDto} object with user data.
     * @throws UserNotFoundException if no User with provided id founded
     */
    @Override
    public UserDto getUser(int id) {
        Optional<User> optionalUser = userDAO.getUser(id);

        User user = optionalUser.orElseThrow(() -> new UserNotFoundException(
                String.format(NO_USER_WITH_ID_FOUND, id),
                String.format(ERROR_CODE_USER_BY_ID_NOT_FOUND_FAILED, id),
                String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDtoUserMapper.toDTO(user);
    }

    /**
     * Invokes DAO method to get List of all Users from database.
     *
     * @param page is {@link Page} object with page number and page size
     * @return List of {@link UserDto} objects with user data.
     */
    @Override
    public List<UserDto> getUsers(Page page) {
        List<User> userList = userDAO.getUsers(page.getPage(), page.getSize());

        return EntityDtoUserMapper.toDTO(userList);
    }
}
