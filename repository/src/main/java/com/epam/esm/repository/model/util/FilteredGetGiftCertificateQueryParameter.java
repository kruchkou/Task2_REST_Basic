package com.epam.esm.repository.model.util;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class FilteredGetGiftCertificateQueryParameter {

    private String name;
    private String description;
    private List<Filter> price;
    private List<Filter> duration;
    private List<String> tagName;
    private SortBy sortBy;
    private SortOrientation sortOrientation;

    public FilteredGetGiftCertificateQueryParameter(String name, String description, List<String> price,
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

    public void setSortBy(String sortBy) {
        this.sortBy = SortBy.valueOf(sortBy.toUpperCase());
    }

    public void setSortOrientation(String sortOrientation) {
        this.sortOrientation = SortOrientation.valueOf(sortOrientation.toUpperCase());
    }

    public boolean isEmpty() {
        return name == null && description == null && tagName == null && sortBy == null && sortOrientation == null;
    }

}
