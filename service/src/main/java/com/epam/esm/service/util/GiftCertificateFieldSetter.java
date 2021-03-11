package com.epam.esm.service.util;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;

public final class GiftCertificateFieldSetter {

    public static GiftCertificate update(GiftCertificate dataFrom, GiftCertificate dataTo) {
        String name = dataFrom.getName();
        if (name != null) {
            dataTo.setName(name);
        }

        String desc = dataFrom.getDescription();
        if (desc != null) {
            dataTo.setDescription(desc);
        }

        Integer price = dataFrom.getPrice();
        if (price != null) {
            dataTo.setPrice(price);
        }

        Integer duration = dataFrom.getDuration();
        if (duration != null) {
            dataTo.setDuration(duration);
        }

        LocalDateTime lastUpdateDate = dataFrom.getLastUpdateDate();
        if (lastUpdateDate != null) {
            dataTo.setLastUpdateDate(lastUpdateDate);
        }

        List<Tag> tagList = dataFrom.getTagList();
        if (tagList != null) {
            dataTo.setTagList(tagList);
        }
        return dataTo;
    }

}