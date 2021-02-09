package com.epam.esm.service.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderParameter {

    @Min(1)
    Integer user;
    List<@Min(1) Integer> gifts;

}
