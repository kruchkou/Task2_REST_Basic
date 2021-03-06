package com.epam.esm.service;

import com.epam.esm.service.model.dto.OrderDto;
import com.epam.esm.service.model.util.CreateOrderParameter;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface provides methods to interact with OrderRepository.
 * Methods should transforms received information into Repository-accepted data and invoke corresponding methods.
 */
public interface OrderService {

    /**
     * Connects to database and returns all Users.
     *
     * @param userID is User ID value.
     * @return List of all {@link OrderDto} with Order data.
     */
    List<OrderDto> getOrdersByUserID(int userID);

    /**
     * Connects to database and returns User by ID.
     *
     * @param id is Order ID value.
     * @return {@link OrderDto} object with Order data.
     */
    OrderDto getOrder(int id);

    /**
     * Connects to database and add an new Order.
     *
     * @param createOrderParameter is {@link CreateOrderParameter} object with data provided
     * @return Created {@link OrderDto} object with Order data.
     */
    OrderDto createOrder(CreateOrderParameter createOrderParameter);

    /**
     * Invokes Repository method to get List of all Orders from database.
     *
     * @param pageable is {@link Pageable} object with page number and page size
     * @return List of {@link OrderDto} objects with order data.
     */
    List<OrderDto> getOrders(Pageable pageable);

}
