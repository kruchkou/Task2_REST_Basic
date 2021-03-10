package com.epam.esm.service.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderParameter {

    @Min(1)
    @NotNull
    private Integer user;
    @NotEmpty
    private List<@Min(1) Integer> gifts;

}
