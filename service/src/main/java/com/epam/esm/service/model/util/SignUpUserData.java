package com.epam.esm.service.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SignUpUserData {

    @NotNull
    @Size(min = 3, max = 45)
    @Pattern(regexp = "^[\\wа-яА-Я]+\\h[\\wа-яА-Я]+$")
    private String name;

    @NotNull
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^[a-z0-9_-]+$")
    private String login;

    @NotNull
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^[a-z0-9_.-]+$")
    private String password;

}
