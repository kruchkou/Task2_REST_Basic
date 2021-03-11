package com.epam.esm.repository.util;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftCertificateFieldUpdater {

    /**
     * Set field values to giftToUpdate from non-null dataFrom fields
     *
     * @param giftToUpdate {@link GiftCertificate} object that is needed to be updated
     * @param dataFrom     {@link GiftCertificate} that contains data to update
     */
    public void updateFields(GiftCertificate giftToUpdate, GiftCertificate dataFrom) {

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
