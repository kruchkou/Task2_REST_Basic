package com.epam.esm.repository.dao;

import com.epam.esm.repository.model.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides methods to interact with Order data from database.
 * Methods should connect to database and manipulate with data(create, read).
 */
public interface OrderDAO {

    /**
     * Connects to database and returns all Users.
     *
     * @param userID is User ID value.
     * @return List of all {@link Order} entities from database.
     */
    List<Order> getOrdersByUserID(int userID);

    /**
     * Connects to database and returns User by ID.
     *
     * @param id is Order ID value.
     * @return Optional of {@link Order} entity from database.
     */
    Optional<Order> getOrder(int id);

    /**
     * Connects to database and returns all Orders.
     *
     * @param page is page number
     * @param size is page size
     * @return List of all {@link Order} entities from database.
     */
    List<Order> getOrders(int page, int size);

    /**
     * Connects to database and add an new Order.
     *
     * @param order {@link Order} is entity with data for creating Order.
     * @return Created {@link Order} entity from database
     */
    Order createOrder(Order order);

}
