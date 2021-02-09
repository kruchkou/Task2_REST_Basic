package com.epam.esm.service.util.validator;

import com.epam.esm.service.model.dto.TagDto;

/**
 * Class is Validator that validates received Tag data
 */
public final class TagValidator {

    private TagValidator() {
    }

    /**
     * Validates data for creation
     *
     * @param tagDto is {@link TagDto} object with data to create new Tag
     * @return true if data is OK, false if data failed validation
     */
    public static boolean validateForCreate(TagDto tagDto) {
        return validateName(tagDto.getName());
    }

    /**
     * Validates name
     *
     * @param name parameter of Tag
     * @return true if data is OK, false if data failed validation
     */
    public static boolean validateName(String name) {
        final int MAX_NAME_LENGTH = 45;

        return name != null && name.length() < MAX_NAME_LENGTH;
    }

}
