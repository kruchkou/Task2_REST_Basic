package com.epam.esm.repository.dao.util;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;

import java.util.List;

public class GiftCertificateFieldUpdater {

    private GiftCertificateFieldUpdater() {
    }

    /**
     * Set field values to giftToUpdate from non-null dataFrom fields
     *
     * @param giftToUpdate {@link GiftCertificate} object that is needed to be updated
     * @param dataFrom {@link GiftCertificate} that contains data to update
     */
    public static void updateFields(GiftCertificate giftToUpdate, GiftCertificate dataFrom) {

        Integer price = dataFrom.getPrice();
        if (price != null) {
            giftToUpdate.setPrice(price);
        }

        Integer duration = dataFrom.getDuration();
        if (duration != null) {
            giftToUpdate.setDuration(duration);
        }

        String name = dataFrom.getName();
        if (name != null) {
            giftToUpdate.setName(name);
        }

        String description = dataFrom.getDescription();
        if (description != null) {
            giftToUpdate.setDescription(description);
        }

        List<Tag> tagList = dataFrom.getTagList();
        if (tagList != null) {
            giftToUpdate.setTagList(tagList);
        }
        
    }

}
