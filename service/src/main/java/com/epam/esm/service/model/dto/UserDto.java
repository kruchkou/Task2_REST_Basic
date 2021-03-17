package com.epam.esm.service.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String name;
    private String login;
    private List<UserOrderDto> orderList;
}
