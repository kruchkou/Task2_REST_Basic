package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.GiftCertificateModelAssembler;
import com.epam.esm.controller.util.assembler.OrderModelAssembler;
import com.epam.esm.controller.util.assembler.TagModelAssembler;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.model.dto.OrderDto;
import com.epam.esm.service.model.dto.TagDto;
import com.epam.esm.service.model.util.CreateOrderParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final TagService tagService;
    private final GiftCertificateService giftCertificateService;
    private final OrderModelAssembler orderModelAssembler;
    private final TagModelAssembler tagModelAssembler;
    private final GiftCertificateModelAssembler giftCertificateModelAssembler;

    @Autowired
    public OrderController(OrderService orderService, TagService tagService,
                           GiftCertificateService giftCertificateService, OrderModelAssembler orderModelAssembler,
                           TagModelAssembler tagModelAssembler,
                           GiftCertificateModelAssembler giftCertificateModelAssembler) {

        this.orderService = orderService;
        this.tagService = tagService;
        this.giftCertificateService = giftCertificateService;
        this.orderModelAssembler = orderModelAssembler;
        this.tagModelAssembler = tagModelAssembler;
        this.giftCertificateModelAssembler = giftCertificateModelAssembler;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<EntityModel<OrderDto>> getOrders(Pageable pageable) {
        return orderModelAssembler.toModel(orderService.getOrders(pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public EntityModel<OrderDto> newOrder(@Valid @RequestBody CreateOrderParameter createOrderParameter) {
        return orderModelAssembler.toModel(orderService.createOrder(createOrderParameter));
    }

    @GetMapping(params = "user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<EntityModel<OrderDto>> getOrdersByUserID(@RequestParam(value = "user") int userID) {
        return orderModelAssembler.toModel(orderService.getOrdersByUserID(userID));
    }

    @GetMapping("/{id}/gifts")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<EntityModel<GiftCertificateDto>> getGiftCertificateListByOrderID(@PathVariable int id) {
        return giftCertificateModelAssembler.toModel(giftCertificateService.getCertificateListByOrderID(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public EntityModel<OrderDto> getOrderByID(@PathVariable int id) {
        return orderModelAssembler.toModel(orderService.getOrder(id));
    }

    @GetMapping("highest-cost/user/most-used-tag")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public EntityModel<TagDto> getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders() {
        return tagModelAssembler.toModel(tagService.getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders());
    }

}
