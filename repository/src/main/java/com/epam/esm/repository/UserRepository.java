package com.epam.esm.repository;

import com.epam.esm.repository.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Connects to database and returns User by login.
     *
     * @param login is User login.
     * @return Optional of {@link User} entity from database.
     */
    Optional<User> findByLogin(String login);

    /**
     * Connects to database and returns User by ID.
     *
     * @param id is User ID value.
     * @return Optional of {@link User} entity from database.
     */
    Optional<User> findById(int id);

    /**
     * Connects to database to get user with the highest cost of all orders
     *
     * @return {@link User} object with user data.
     */
    @Query("SELECT orders.user FROM Order orders GROUP BY orders.user ORDER BY SUM(orders.price) DESC")
    Page<User> findFirstByQuery(Pageable pageable);

}
