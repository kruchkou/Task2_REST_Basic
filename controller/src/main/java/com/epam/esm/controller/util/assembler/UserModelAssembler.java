package com.epam.esm.controller.util.assembler;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.model.dto.UserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDto,
        EntityModel<UserDto>> {

    @Override
    public EntityModel<UserDto> toModel(UserDto userDto) {
        return EntityModel.of(userDto,
                linkTo(methodOn(UserController.class).getUserByID(userDto.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers(Page.def())).withRel("Users"),
                linkTo(methodOn(OrderController.class).getOrdersByUserID(userDto.getId())).withRel("Orders"));
    }

    public List<EntityModel<UserDto>> toModel(List<UserDto> userDto) {
        return userDto.stream().map(this::toModel).collect(Collectors.toList());
    }

}
