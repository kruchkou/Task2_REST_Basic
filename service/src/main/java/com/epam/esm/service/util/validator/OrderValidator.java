package com.epam.esm.service.util.validator;

import com.epam.esm.service.model.util.CreateOrderParameter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderValidator {

    public static boolean validateCreateOrder(CreateOrderParameter createOrderParameter) {
        return validateGiftList(createOrderParameter.getGifts());
    }

    /**
     * Validates Order gifts list
     *
     * @param giftList is GiftList of Order
     * @return true if data is OK, false if data failed validation
     */
    private static boolean validateGiftList(List<Integer> giftList) {
        if (giftList == null) {
            return false;
        }

        boolean giftsValidated = true;

        List<Integer> listWithoutDuplicates = giftList.stream().distinct().collect(Collectors.toList());

        if(!giftList.equals(listWithoutDuplicates)) {
            giftsValidated = false;
        }

        return giftsValidated;
    }

}
