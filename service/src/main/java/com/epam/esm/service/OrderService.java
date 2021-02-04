package com.epam.esm.service;

import com.epam.esm.service.model.dto.OrderDTO;
import com.epam.esm.service.model.dto.UserDTO;

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
     * @param userID is User ID value
     * @param giftID is GiftCertificate ID value
     * @return Created {@link OrderDTO} object with Order data.
     */
    OrderDTO createOrder(int userID, int giftID);

    /**
     * Invokes DAO method to get List of all Orders from database.
     *
     * @return List of {@link OrderDTO} objects with order data.
     */
    List<OrderDTO> getOrders();

}
