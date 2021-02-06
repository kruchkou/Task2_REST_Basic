package com.epam.esm.repository.model.util;

import lombok.Data;

@Data
public class Filter {

    private FilterType type;
    private int value;

}
