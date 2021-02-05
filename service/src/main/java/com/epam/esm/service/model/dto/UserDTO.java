package com.epam.esm.service.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    Integer id;
    String name;
    List<OrderDTO> orderList;
}
