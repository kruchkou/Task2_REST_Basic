package com.epam.esm.repository;

import com.epam.esm.repository.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    /**
     * Connects to database and returns User by ID.
     *
     * @param id is Order ID value.
     * @return Optional of {@link Order} entity from database.
     */
    Optional<Order> findById(int id);

    /**
     * Connects to database and returns Orders by user id
     *
     * @param id is Order ID value.
     * @return List of all {@link Order} entities from database.
     */
    List<Order> findByUser_Id(int id);

}
