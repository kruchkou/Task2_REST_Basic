package com.epam.esm.service.util.validator;

import com.epam.esm.service.model.dto.TagDTO;

/**
 * Class is Validator that validates received Tag data
 */
public final class TagValidator {

    private TagValidator() {
    }

    /**
     * Validates data for creation
     *
     * @param tagDTO is {@link TagDTO} object with data to create new Tag
     * @return true if data is OK, false if data failed validation
     */
    public static boolean validateForCreate(TagDTO tagDTO) {
        return validateName(tagDTO.getName());
    }

    /**
     * Validates name
     *
     * @param name parameter of Tag
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validateName(String name) {
        final int MAX_NAME_LENGTH = 45;

        return name != null && name.length() < MAX_NAME_LENGTH;
    }

}
