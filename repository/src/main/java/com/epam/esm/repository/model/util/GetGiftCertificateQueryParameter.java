package com.epam.esm.repository.model.util;

import org.springframework.util.StringUtils;

public class GetGiftCertificateQueryParameter {

    private String name;
    private String description;
    private String tagName;
    private SortBy sortBy;
    private SortOrientation sortOrientation;

    public GetGiftCertificateQueryParameter() {

    }

    public GetGiftCertificateQueryParameter(String tagName, String name, String description, String sortBy, String sortOrientation) {
        if (!StringUtils.isEmpty(tagName)) {
            setTagName(tagName);
        }
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
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = SortBy.valueOf(sortBy.toUpperCase());
    }

    public SortOrientation getSortOrientation() {
        return sortOrientation;
    }

    public void setSortOrientation(String sortOrientation) {
        this.sortOrientation = SortOrientation.valueOf(sortOrientation.toUpperCase());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEmpty() {
        return name == null && description == null && tagName == null && sortBy == null && sortOrientation == null;
    }

}
