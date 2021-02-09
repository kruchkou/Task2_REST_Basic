package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.UserModelAssembler;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.UserService;
import com.epam.esm.service.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    @Autowired
    public UserController(UserService userService, com.epam.esm.controller.util.assembler.UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping
    public List<EntityModel<UserDto>> getUsers(@Valid Page page) {
        return userModelAssembler.toModel(userService.getUsers(page));
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserByID(@PathVariable int id) {
        return userModelAssembler.toModel(userService.getUser(id));
    }

}
