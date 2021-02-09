package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.GiftCertificateModelAssembler;
import com.epam.esm.controller.util.assembler.OrderModelAssembler;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.model.dto.OrderDto;
import com.epam.esm.service.model.util.CreateOrderParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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
    private final GiftCertificateService giftCertificateService;
    private final OrderModelAssembler orderModelAssembler;
    private final GiftCertificateModelAssembler giftCertificateModelAssembler;

    @Autowired
    public OrderController(OrderService orderService, GiftCertificateService giftCertificateService,
                           OrderModelAssembler orderModelAssembler,
                           GiftCertificateModelAssembler giftCertificateModelAssembler) {
        this.orderService = orderService;
        this.giftCertificateService = giftCertificateService;
        this.orderModelAssembler = orderModelAssembler;
        this.giftCertificateModelAssembler = giftCertificateModelAssembler;
    }

    @GetMapping
    public List<EntityModel<OrderDto>> getOrders(@Valid Page page) {
        return orderModelAssembler.toModel(orderService.getOrders(page));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<OrderDto> newOrder(@Valid @RequestBody CreateOrderParameter createOrderParameter) {
        return orderModelAssembler.toModel(orderService.createOrder(createOrderParameter));
    }

    @GetMapping(params = "user")
    public List<EntityModel<OrderDto>> getOrdersByUserID(@RequestParam(value = "user") int userID) {
        return orderModelAssembler.toModel(orderService.getOrdersByUserID(userID));
    }

    @GetMapping("/{id}/gifts")
    public List<EntityModel<GiftCertificateDto>> getGiftCertificateListByOrderID(@PathVariable int id) {
        return giftCertificateModelAssembler.toModel(giftCertificateService.getCertificateListByOrderID(id));
    }

    @GetMapping("/{id}")
    public EntityModel<OrderDto> getOrderByID(@PathVariable int id) {
        return orderModelAssembler.toModel(orderService.getOrder(id));
    }


}
