package com.epam.esm.repository.model.util;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
public class GetGiftCertificateQueryParameter {

    private String name;
    private String description;
    private String tagName;
    private SortBy sortBy;
    private SortOrientation sortOrientation;

    public GetGiftCertificateQueryParameter(String name, String description, String sortBy, String sortOrientation, String tagName) {
        if (!StringUtils.isEmpty(name)) {
            setName(name);
        }
        if (!StringUtils.isEmpty(description)) {
            setDescription(description);
        }
        if (!StringUtils.isEmpty(sortBy)) {
            setSortBy(sortBy);
        }
        if (!StringUtils.isEmpty(sortOrientation)) {
            setSortOrientation(sortOrientation);
        }
        if (!StringUtils.isEmpty(tagName)) {
            setTagName(tagName);
        }
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
