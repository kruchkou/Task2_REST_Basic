package com.epam.esm.repository.impl;

import com.epam.esm.repository.CustomizedTagRepository;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

public class CustomizedTagRepositoryImpl implements CustomizedTagRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Tag getMostWidelyUsedTag(int userID) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tag> tagQuery = criteriaBuilder.createQuery(Tag.class);

        Root<User> userRoot = tagQuery.from(User.class);
        ListJoin<User, Order> orderList = userRoot.joinList(User_.ORDER_LIST);
        ListJoin<Order, GiftCertificate> giftList = orderList.joinList(Order_.GIFT_LIST);
        ListJoin<GiftCertificate, Tag> tagList = giftList.joinList(GiftCertificate_.TAG_LIST);

        Expression orderID = tagList.get(Order_.ID);
        tagQuery
                .select(tagList)
                .where(criteriaBuilder.equal(userRoot.get(User_.ID), userID))
                .groupBy(orderID)
                .orderBy(criteriaBuilder.desc(criteriaBuilder.count(orderID)));

        return em.createQuery(tagQuery).setMaxResults(1).getSingleResult();
    }

}
