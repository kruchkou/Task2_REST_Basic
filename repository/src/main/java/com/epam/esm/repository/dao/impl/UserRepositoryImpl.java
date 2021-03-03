package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryImpl extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);

    Optional<User> findById(long id);

    @Query("SELECT orders.user FROM Order orders GROUP BY orders.user ORDER BY SUM(orders.price) DESC")
    User findFirstByQuery();

}
