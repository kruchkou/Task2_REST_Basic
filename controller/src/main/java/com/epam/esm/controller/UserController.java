package com.epam.esm.controller;

import com.epam.esm.controller.model.util.AuthResponse;
import com.epam.esm.controller.util.JwtProvider;
import com.epam.esm.controller.util.assembler.UserModelAssembler;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.impl.UserServiceRepo;
import com.epam.esm.service.model.dto.UserDto;
import com.epam.esm.service.model.util.AuthRequest;
import com.epam.esm.service.model.util.SignUpUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserServiceRepo userService;
    private final UserModelAssembler userModelAssembler;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(UserServiceRepo userService, UserModelAssembler userModelAssembler, JwtProvider jwtProvider) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDto> signUp(@Valid SignUpUserData signUpUserData) {
        return userModelAssembler.toModel(userService.signUp(signUpUserData));
    }

    @GetMapping
    public List<EntityModel<UserDto>> getUsers(Pageable pageable) {
        return userModelAssembler.toModel(userService.getUsers(pageable));
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserByID(@PathVariable int id) {
        return userModelAssembler.toModel(userService.getUser(id));
    }

    @PostMapping("/auth")
    public AuthResponse signIn(AuthRequest authRequest) {
        UserDto user = userService.signIn(authRequest);
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }

}
