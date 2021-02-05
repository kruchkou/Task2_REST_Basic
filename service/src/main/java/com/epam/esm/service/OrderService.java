package com.epam.esm.service;

import com.epam.esm.service.exception.impl.GiftCertificateNotFoundException;
import com.epam.esm.service.exception.impl.UserNotFoundException;
import com.epam.esm.service.model.dto.OrderDTO;
import com.epam.esm.service.model.dto.UserDTO;
import com.epam.esm.service.model.util.CreateOrderParameter;

import java.util.List;

/**
 * Interface provides methods to interact with OrderDAO.
 * Methods should transforms received information into DAO-accepted data and invoke corresponding methods.
 */
public interface OrderService {

    /**
     * Connects to database and returns all Users.
     *
     * @param userID is User ID value.
     * @return List of all {@link OrderDTO} with Order data.
     */
    List<OrderDTO> getOrdersByUserID(int userID);

    /**
     * Connects to database and returns User by ID.
     *
     * @param id is Order ID value.
     * @return {@link OrderDTO} object with Order data.
     */
    OrderDTO getOrder(int id);

    /**
     * Connects to database and add an new Order.
     *
     * @param createOrderParameter is {@link CreateOrderParameter} object with data provided
     * @return Created {@link OrderDTO} object with Order data.
     */
    OrderDTO createOrder(CreateOrderParameter createOrderParameter);

    /**
     * Invokes DAO method to get List of all Orders from database.
     *
     * @return List of {@link OrderDTO} objects with order data.
     */
    List<OrderDTO> getOrders();

}
