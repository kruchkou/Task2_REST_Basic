package com.epam.esm.controller;

import com.epam.esm.service.OrderService;
import com.epam.esm.service.model.dto.OrderDTO;
import com.epam.esm.service.model.util.CreateOrderParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO newOrder(@Valid @RequestBody CreateOrderParameter createOrderParameter) {
        return orderService.createOrder(createOrderParameter);
    }

    @GetMapping(params = "user")
    public List<OrderDTO> getOrdersByUserID(@RequestParam(value = "user") int userID) {
        return orderService.getOrdersByUserID(userID);
    }

}
