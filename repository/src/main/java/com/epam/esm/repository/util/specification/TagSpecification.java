package com.epam.esm.repository.util.specification;

import com.epam.esm.repository.model.entity.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;

public final class TagSpecification {

    /**
     * Method to provide {@link Specification<Tag>} to find Tags by Gift id.
     *
     * @param giftID is gift id.
     * @return {@link Specification<Tag>} object to find most widely used tag from user.
     */
    public static Specification<Tag> tagListByGiftID(int giftID) {
        return (Specification<Tag>) (root, query, cb) -> {

            Root<GiftCertificate> giftRoot = query.from(GiftCertificate.class);
            ListJoin<GiftCertificate, Tag> tagList = giftRoot.joinList(GiftCertificate_.TAG_LIST);

            return cb.equal(tagList.get(GiftCertificate_.ID), giftID);
        };
    }

    /**
     * Method to provide {@link Specification<Tag>} to find most widely used tag from user.
     *
     * @param userID is user id.
     * @return {@link Specification<Tag>} object to find most widely used tag from user.
     */
    public static Specification<Tag> getMostWidelyUsedTagFromUser(int userID) {
        return (Specification<Tag>) (root, query, cb) -> {

            Root<User> userRoot = query.from(User.class);
            ListJoin<User, com.epam.esm.repository.model.entity.Order> orderList = userRoot.joinList(User_.ORDER_LIST);
            ListJoin<Order, GiftCertificate> giftList = orderList.joinList(Order_.GIFT_LIST);
            ListJoin<GiftCertificate, Tag> tagList = giftList.joinList(GiftCertificate_.TAG_LIST);

            Expression orderID = tagList.get(Order_.ID);

            query.groupBy(orderID).orderBy(cb.desc(cb.count(orderID)));

            return cb.equal(userRoot.get(User_.ID), userID);
        };
    }

}