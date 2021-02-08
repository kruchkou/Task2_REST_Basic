package com.epam.esm.service.model.dto;

import com.epam.esm.service.model.util.UserOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    Integer id;
    String name;
    List<UserOrder> orderList;
}
