package com.epam.esm.controller;

import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.UserService;
import com.epam.esm.service.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers(@Valid Page page) {
        return userService.getUsers(page);
    }

    @GetMapping("/{id}")
    public UserDto getGiftCertificateByID(@PathVariable int id) {
        return userService.getUser(id);
    }

}
