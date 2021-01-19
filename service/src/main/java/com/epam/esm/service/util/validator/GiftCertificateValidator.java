package com.epam.esm.service.util.validator;

import com.epam.esm.service.model.dto.GiftCertificateDTO;

/**
 * Class is Validator that validates received GiftCertificate data
 */
public final class GiftCertificateValidator {

    private GiftCertificateValidator() {
    }

    /**
     * Validates data for creation
     *
     * @param giftCertificateDTO is {@link GiftCertificateDTO} object with data to create new GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    public static boolean validateForCreate(GiftCertificateDTO giftCertificateDTO) {
        if (!validateName(giftCertificateDTO.getName())) {
            return false;
        }
        if (!validateDesc(giftCertificateDTO.getDescription())) {
            return false;
        }
        if (!validatePrice(giftCertificateDTO.getPrice())) {
            return false;
        }
        return validateDuration(giftCertificateDTO.getDuration());
    }

    /**
     * Validates duration
     *
     * @param duration parameter of GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validateDuration(int duration) {
        return duration > 0;
    }

    /**
     * Validates price
     *
     * @param price parameter of GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validatePrice(int price) {
        return price > 0;
    }

    /**
     * Validates name
     *
     * @param name parameter of GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validateName(String name) {
        final int MAX_NAME_LENGTH = 45;

        return name != null && name.length() < MAX_NAME_LENGTH;
    }

    /**
     * Validates description
     *
     * @param description parameter of GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validateDesc(String description) {
        final int MAX_DESC_LENGTH = 200;

        return description != null && description.length() < MAX_DESC_LENGTH;
    }

}
