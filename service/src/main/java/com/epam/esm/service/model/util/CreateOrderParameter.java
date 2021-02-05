package com.epam.esm.service.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderParameter {

    Integer userID;
    List<Integer> giftIDList;

}
