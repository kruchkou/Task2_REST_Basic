package com.epam.esm.repository.util.specification;

import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.*;
import com.epam.esm.repository.model.util.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class GiftCertificateSpecification {

    private static final String ANY_SYMBOL = "%";

    /**
     * Method to provide {@link Specification<Tag>} to find Tags by Gift id.
     *
     * @param orderId is gift id.
     * @return {@link Specification<Tag>} object to find most widely used tag from user.
     */
    public static Specification<GiftCertificate> giftCertificateListByOrderId(int orderId) {
        return (Specification<GiftCertificate>) (root, query, cb) -> {

            Root<Order> giftRoot = query.from(Order.class);
            ListJoin<Order, GiftCertificate> orderList = giftRoot.joinList(GiftCertificate_.TAG_LIST);

            return cb.equal(orderList.get(Order_.ID), orderId);
        };
    }

    /**
     * Returns Specification of {@link GiftCertificate} objects filtered by received parameters.
     *
     * @param giftCertificateQueryParameter {@link GetGiftCertificateQueryParameter} object with required params
     * @return {@link Specification<GiftCertificate>} object to find most widely used tag from user.
     */
    public static Specification<GiftCertificate> findByQueryParameter(
            GetGiftCertificateQueryParameter giftCertificateQueryParameter) {

        return (Specification<GiftCertificate>) (root, query, cb) -> {

            List<Predicate> predicateList = new ArrayList<>();

            String name = giftCertificateQueryParameter.getName();

            if (name != null) {
                Predicate predicate = cb.like(root.get(GiftCertificate_.NAME),
                        ANY_SYMBOL + name + ANY_SYMBOL);
                predicateList.add(predicate);
            }

            String description = giftCertificateQueryParameter.getDescription();
            if (description != null) {
                Predicate predicate = cb.like(root.get(GiftCertificate_.DESCRIPTION),
                        ANY_SYMBOL + description + ANY_SYMBOL);
                predicateList.add(predicate);
            }

            List<Filter> priceFilterList = giftCertificateQueryParameter.getPrice();
            if (priceFilterList != null) {
                Path<GiftCertificate> path = root.get(GiftCertificate_.PRICE);

                for (Filter filter : priceFilterList) {
                    Predicate predicate = filterToPredicate(cb, path, filter);
                    predicateList.add(predicate);
                }
            }

            List<Filter> durationFilterList = giftCertificateQueryParameter.getDuration();
            if (durationFilterList != null) {
                Path<GiftCertificate> path = root.get(GiftCertificate_.DURATION);

                for (Filter filter : durationFilterList) {
                    Predicate predicate = filterToPredicate(cb, path, filter);
                    predicateList.add(predicate);
                }
            }

            List<String> tagNameList = giftCertificateQueryParameter.getTagName();
            if (tagNameList != null) {
                List<String> tagNamesWithoutDuplicates = tagNameList.stream().distinct().collect(Collectors.toList());

                if (tagNameList.size() != tagNamesWithoutDuplicates.size()) {
                    return cb.disjunction();
                }

                ListJoin<Tag, GiftCertificate> tagJoin = root.joinList(GiftCertificate_.TAG_LIST);
                Path<Tag> tagList = tagJoin.get(Tag_.NAME);

                tagNameList.forEach(tag -> predicateList.add(cb.equal(tagList, tag)));
            }

            String columnNameToSort = null;

            Sort sort = giftCertificateQueryParameter.getSort();

            if (sort != null) {
                SortBy sortBy = sort.getSortBy();
                SortOrientation sortOrientation = sort.getSortOrientation();

                switch (sortBy) {
                    case NAME:
                        columnNameToSort = GiftCertificate_.NAME;
                        break;
                    case DATE:
                        columnNameToSort = GiftCertificate_.LAST_UPDATE_DATE;
                        break;
                }

                javax.persistence.criteria.Order order = null;

                switch (sortOrientation) {
                    case ASC:
                        order = cb.asc(root.get(columnNameToSort));
                        break;

                    case DESC:
                        order = cb.desc(root.get(columnNameToSort));
                        break;
                }
                query.orderBy(order);
            }

            return cb.and(predicateList.toArray(new Predicate[0]));
        };
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
            default: {
                return criteriaBuilder.equal(expression, value);
            }
        }
    }

}