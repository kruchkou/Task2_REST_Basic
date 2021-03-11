package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.UserModelAssembler;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.service.model.dto.UserDto;
import com.epam.esm.service.model.util.SignUpUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserServiceImpl userService;
    private final UserModelAssembler userModelAssembler;
    //private final JwtProvider jwtProvider;

    @Autowired
    public UserController(UserServiceImpl userService, UserModelAssembler userModelAssembler
    ) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDto> signUp(@Valid SignUpUserData signUpUserData) {
        return userModelAssembler.toModel(userService.signUp(signUpUserData));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<EntityModel<UserDto>> getUsers(Pageable pageable) {
        return userModelAssembler.toModel(userService.getUsers(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public EntityModel<UserDto> getUserByID(@PathVariable int id) {
        return userModelAssembler.toModel(userService.getUser(id));
    }

}
