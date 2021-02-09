package com.epam.esm.controller.util.assembler;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.model.dto.OrderDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<OrderDto,
        EntityModel<OrderDto>> {

    @Override
    public EntityModel<OrderDto> toModel(OrderDto orderDto) {
        return EntityModel.of(orderDto,
                linkTo(methodOn(OrderController.class).getOrderByID(orderDto.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getOrders(new Page())).withRel("Orders"),
                linkTo(methodOn(OrderController.class).getGiftCertificateListByOrderID(orderDto.getId())).withRel("Gifts"),
                linkTo(methodOn(UserController.class).getUserByID(orderDto.getId())).withRel("User"));
    }

    public List<EntityModel<OrderDto>> toModel(List<OrderDto> orderDto) {
        return orderDto.stream().map(this::toModel).collect(Collectors.toList());
    }

}
