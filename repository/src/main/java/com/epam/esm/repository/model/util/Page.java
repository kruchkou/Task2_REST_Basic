package com.epam.esm.repository.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class Page {

    @Min(1)
    private int page = 1;

    @Min(1)
    private int size = 20;

    public static Page def() {
        Page page = new Page();
        page.setPage(1);
        page.setSize(20);

        return page;
    }

}
