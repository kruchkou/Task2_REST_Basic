package com.epam.esm.service.util.validator;

import com.epam.esm.service.model.dto.GiftCertificateDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class is Validator that validates received GiftCertificate data
 */
public final class GiftCertificateValidator {

    private GiftCertificateValidator() {
    }

    /**
     * Validates data for creation
     *
     * @param giftCertificateDto is {@link GiftCertificateDto} object with data to create new GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    public static boolean validateForCreate(GiftCertificateDto giftCertificateDto) {
        String name = giftCertificateDto.getName();
        if (name == null || !validateName(giftCertificateDto.getName())) {
            return false;
        }

        String description = giftCertificateDto.getDescription();
        if (description == null || !validateDesc(giftCertificateDto.getDescription())) {
            return false;
        }

        Integer price = giftCertificateDto.getPrice();
        if (price == null || !validatePrice(giftCertificateDto.getPrice())) {
            return false;
        }

        Integer duration = giftCertificateDto.getDuration();
        if (duration == null || !validateDuration(giftCertificateDto.getDuration())) {
            return false;
        }
        return validateTagList(giftCertificateDto.getTags());
    }

    public static boolean validateForUpdate(GiftCertificateDto giftCertificateDto) {
        String name = giftCertificateDto.getName();
        if (name != null && !validateName(giftCertificateDto.getName())) {
            return false;
        }

        String description = giftCertificateDto.getDescription();
        if (description != null && !validateDesc(giftCertificateDto.getDescription())) {
            return false;
        }

        Integer price = giftCertificateDto.getPrice();
        if (price != null && !validatePrice(giftCertificateDto.getPrice())) {
            return false;
        }

        Integer duration = giftCertificateDto.getDuration();
        if (duration != null && !validateDuration(giftCertificateDto.getDuration())) {
            return false;
        }
        return validateTagList(giftCertificateDto.getTags());
    }

    /**
     * Validates duration
     *
     * @param duration parameter of GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validateDuration(Integer duration) {
        return duration > 0;
    }

    /**
     * Validates price
     *
     * @param price parameter of GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validatePrice(Integer price) {
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

        return name.length() < MAX_NAME_LENGTH;
    }

    /**
     * Validates description
     *
     * @param description parameter of GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validateDesc(String description) {
        final int MAX_DESC_LENGTH = 200;

        return description.length() < MAX_DESC_LENGTH;
    }

    /**
     * Validates Tag names list
     *
     * @param tagNamesList parameter of GiftCertificate
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validateTagList(List<String> tagNamesList) {
        if (tagNamesList == null) {
            return true;
        }

        boolean tagsValidated = true;

        List<String> listWithoutDuplicates = tagNamesList.stream().distinct().collect(Collectors.toList());

        if(!tagNamesList.equals(listWithoutDuplicates)) {
            tagsValidated = false;
        }

        for (String tagName : tagNamesList) {
            if (!TagValidator.validateName(tagName)) {
                tagsValidated = false;
                break;
            }
        }

        return tagsValidated;
    }
}
