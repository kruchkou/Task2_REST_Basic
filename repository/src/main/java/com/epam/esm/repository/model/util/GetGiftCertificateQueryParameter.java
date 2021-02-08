package com.epam.esm.repository.model.util;


import com.epam.esm.repository.dao.util.FilterBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import java.util.List;

@Data
@NoArgsConstructor
public class GetGiftCertificateQueryParameter {

    private String name;
    private String description;
    private List<Filter> price;
    private List<Filter> duration;
    private List<String> tagName;
    private SortBy sortBy;
    private SortOrientation sortOrientation;
    @Valid
    private Page page = new Page();

    public GetGiftCertificateQueryParameter(String name, String description, List<String> price,
                                            List<String> duration, String sortBy,
                                            String sortOrientation,
                                            List<String> tagName) {
        if (!StringUtils.isEmpty(name)) {
            setName(name);
        }
        if (!StringUtils.isEmpty(description)) {
            setDescription(description);
        }
        if (price != null && !price.isEmpty()) {
            setPrice(price);
        }
        if (duration != null && !duration.isEmpty()) {
            setDuration(duration);
        }
        if (!StringUtils.isEmpty(sortBy)) {
            setSortBy(sortBy);
        }
        if (!StringUtils.isEmpty(sortOrientation)) {
            setSortOrientation(sortOrientation);
        }
        if (tagName != null && !tagName.isEmpty()) {
            this.tagName = tagName;
        }
    }


    public void setPrice(List<String> price) {
        this.price = FilterBuilder.build(price);
    }
    public void setDuration(List<String> duration) {
        this.duration = FilterBuilder.build(duration);
    }

    public void setPage(int page) {
        this.page.setPage(page);
    }

    public void setSize(int size) {
        this.page.setSize(size);
    }

    public void setSortBy(String sortBy) {
        this.sortBy = SortBy.valueOf(sortBy.toUpperCase());
    }

    public void setSortOrientation(String sortOrientation) {
        this.sortOrientation = SortOrientation.valueOf(sortOrientation.toUpperCase());
    }

    public boolean isEmpty() {
        return name == null && description == null && price == null && duration == null &&
                tagName == null && sortBy == null && sortOrientation == null;
    }

}
