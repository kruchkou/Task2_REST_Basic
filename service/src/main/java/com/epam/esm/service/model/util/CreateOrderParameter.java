package com.epam.esm.service.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderParameter {

    @NotNull(message = "{validation.user_id_null}")
    Integer user;
    @NotEmpty(message = "{validation.gift_id_list_empty}")
    List<@Valid Integer> gifts;

}
