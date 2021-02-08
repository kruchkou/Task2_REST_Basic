package com.epam.esm.controller;

import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.model.dto.OrderDto;
import com.epam.esm.service.model.util.CreateOrderParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDto> getOrders(@Valid Page page) {
        return orderService.getOrders(page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto newOrder(@Valid @RequestBody CreateOrderParameter createOrderParameter) {
        return orderService.createOrder(createOrderParameter);
    }

    @GetMapping(params = "user")
    public List<OrderDto> getOrdersByUserID(@RequestParam(value = "user") int userID) {
        return orderService.getOrdersByUserID(userID);
    }

}
