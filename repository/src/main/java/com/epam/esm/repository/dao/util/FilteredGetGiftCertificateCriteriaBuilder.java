package com.epam.esm.repository.dao.util;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.GiftCertificate_;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.entity.Tag_;
import com.epam.esm.repository.model.util.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class FilteredGetGiftCertificateCriteriaBuilder {

    /**
     * Symbol for matching any symbol at LIKE sql requests
     */
    private static final String ANY_SYMBOL = "%";

    private FilteredGetGiftCertificateCriteriaBuilder() {
    }

    /**
     * Returns {@link CriteriaQuery} object with criteria applied
     *
     * @param criteriaBuilder               {@link CriteriaBuilder} object
     * @param giftCertificateQueryParameter {@link GetGiftCertificateQueryParameter} object with required params
     * @return {@link CriteriaQuery} object
     */
    public static CriteriaQuery<GiftCertificate> build(CriteriaBuilder criteriaBuilder,
                                                       FilteredGetGiftCertificateQueryParameter giftCertificateQueryParameter) {

        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftRoot = criteriaQuery.from(GiftCertificate.class);

        List<Predicate> predicateList = new ArrayList<>();

        String name = giftCertificateQueryParameter.getName();

        if (name != null) {
            Predicate predicate = criteriaBuilder.like(giftRoot.get(GiftCertificate_.NAME),
                    ANY_SYMBOL + name + ANY_SYMBOL);
            predicateList.add(predicate);
        }

        String description = giftCertificateQueryParameter.getDescription();
        if (description != null) {
            Predicate predicate = criteriaBuilder.like(giftRoot.get(GiftCertificate_.DESCRIPTION),
                    ANY_SYMBOL + description + ANY_SYMBOL);
            predicateList.add(predicate);
        }

        List<Filter> priceFilterList = giftCertificateQueryParameter.getPrice();
        if (priceFilterList != null) {
            Path<GiftCertificate> path = giftRoot.get(GiftCertificate_.PRICE);

            for (Filter filter : priceFilterList) {
                Predicate predicate = filterToPredicate(criteriaBuilder, path, filter);
                predicateList.add(predicate);
            }
        }

        List<Filter> durationFilterList = giftCertificateQueryParameter.getDuration();
        if (durationFilterList != null) {
            Path<GiftCertificate> path = giftRoot.get(GiftCertificate_.DURATION);

            for (Filter filter : durationFilterList) {
                Predicate predicate = filterToPredicate(criteriaBuilder, path, filter);
                predicateList.add(predicate);
            }
        }

        List<String> tagNameList = giftCertificateQueryParameter.getTagName();
        if (tagNameList != null) {
            Join<GiftCertificate, Tag> tagJoin = giftRoot.join(GiftCertificate_.TAG_LIST);

            Expression<String> expression = tagJoin.get(Tag_.NAME);

            Predicate nameEqualsPredicate = expression.in(tagNameList);

            predicateList.add(nameEqualsPredicate);
        }

        criteriaQuery.select(giftRoot).where(predicateList.toArray(new Predicate[0]));

        SortBy sortBy = giftCertificateQueryParameter.getSortBy();
        SortOrientation sortOrientation = giftCertificateQueryParameter.getSortOrientation();
        String columnNameToSort = null;

        if (sortBy != null) {
            if (sortOrientation == null) {
                sortOrientation = SortOrientation.ASC;
            }

            if (sortBy == SortBy.NAME) {
                columnNameToSort = GiftCertificate_.NAME;
            } else if (sortBy == SortBy.DATE) {
                columnNameToSort = GiftCertificate_.LAST_UPDATE_DATE;
            }
        }

        if (sortOrientation != null) {
            Order order = null;

            if (columnNameToSort == null) {
                columnNameToSort = GiftCertificate_.NAME;
            }

            switch (sortOrientation) {
                case ASC:
                    order = criteriaBuilder.asc(giftRoot.get(columnNameToSort));
                    break;

                case DESC:
                    order = criteriaBuilder.desc(giftRoot.get(columnNameToSort));
                    break;
            }

            criteriaQuery.orderBy(order);
        }

        return criteriaQuery;
    }

    private static Predicate filterToPredicate(
            CriteriaBuilder criteriaBuilder, Path expression, Filter filter) {

        FilterType filterType = filter.getType();
        int value = filter.getValue();

        switch (filterType) {
            case GT: {
                return criteriaBuilder.gt(expression, value);
            }
            case GTE: {
                return criteriaBuilder.ge(expression, value);
            }
            case LT: {
                return criteriaBuilder.lt(expression, value);
            }
            case LTE: {
                return criteriaBuilder.le(expression, value);
            }
            case EQUALS: {
                return criteriaBuilder.equal(expression, value);
            }
        }
        throw new RuntimeException("BUILDER EXCEPTION");
    }

}
