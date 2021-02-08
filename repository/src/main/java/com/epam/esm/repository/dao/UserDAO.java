package com.epam.esm.repository.dao;

import com.epam.esm.repository.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides methods to interact with User data from database.
 * Methods should connect to database and manipulate with data(read)
 */
public interface UserDAO {

    /**
     * Connects to database and returns all Users.
     * @param page is page number
     * @param size is page size
     * @return List of all {@link User} entities from database.
     */
    List<User> getUsers(int page, int size);

    /**
     * Connects to database and returns User by ID.
     *
     * @param id is User ID value.
     * @return Optional of {@link User} entity from database.
     */
    Optional<User> getUser(int id);

}
