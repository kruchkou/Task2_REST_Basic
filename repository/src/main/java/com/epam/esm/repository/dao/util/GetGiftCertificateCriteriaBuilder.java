package com.epam.esm.repository.dao.util;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.GiftCertificate_;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.entity.Tag_;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.SortBy;
import com.epam.esm.repository.model.util.SortOrientation;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class GetGiftCertificateCriteriaBuilder {

    private static final GetGiftCertificateCriteriaBuilder instance = new GetGiftCertificateCriteriaBuilder();
    private static final String ANY_SYMBOL = "%";

    private GetGiftCertificateCriteriaBuilder() {
    }

    /**
     * Returns instance of the class (Singleton).
     *
     * @return Instance of {@link GetGiftCertificateCriteriaBuilder}.
     */
    public static GetGiftCertificateCriteriaBuilder getInstance() {
        return instance;
    }

    /**
     * Returns {@link CriteriaQuery} object with criteria applied
     *
     * @param criteriaBuilder {@link CriteriaBuilder} object
     * @param giftCertificateQueryParameter {@link GetGiftCertificateQueryParameter} object with required params
     * @return {@link CriteriaQuery} object
     */
    public CriteriaQuery<GiftCertificate> build(CriteriaBuilder criteriaBuilder,
                                                GetGiftCertificateQueryParameter giftCertificateQueryParameter) {

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

        String tagName = giftCertificateQueryParameter.getTagName();
        if (tagName != null) {

            Join<GiftCertificate, Tag> tagJoin = giftRoot.join(GiftCertificate_.TAG_LIST);
            Predicate predicate = criteriaBuilder.equal(tagJoin.get(Tag_.NAME), tagName);
            predicateList.add(predicate);
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
}
