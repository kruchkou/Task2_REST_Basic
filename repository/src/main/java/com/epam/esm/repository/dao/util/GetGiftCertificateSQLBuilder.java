package com.epam.esm.repository.dao.util;

import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.GiftCertificateSQL;
import com.epam.esm.repository.model.util.SortBy;
import com.epam.esm.repository.model.util.SortOrientation;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is builder, that transforms parameters from GET request into SQL query and array of parameters.
 */
public final class GetGiftCertificateSQLBuilder {

    private final static GetGiftCertificateSQLBuilder instance = new GetGiftCertificateSQLBuilder();

    private final static String SPLIT_PARAM_STRING = " AND ";

    private final static String SELECT_SQL = "SELECT * from gift_certificate gifts ";
    private final static String SELECT_WITH_TAG_NAME_SQL = "SELECT * from gift_certificate gifts " +
            "INNER JOIN gift_tag link ON gifts.id = link.gift " +
            "INNER JOIN tag tags ON link.tag = tags.id ";
    private final static String WHERE_SQL = "WHERE (";
    private final static String ADD_NAME_SQL = "gifts.name REGEXP ?";
    private final static String ADD_DESCRIPTION_SQL = "description REGEXP ?";
    private final static String ADD_TAG_NAME_PARAM = "tags.name = ?";
    private final static String CLOSE_WHERE_SQL = ")";
    private final static String ORDER_BY_SQL = " ORDER BY ";
    private final static String NAME_PARAM = "gifts.name";
    private final static String LAST_UPDATE_DATE_PARAM = "last_update_date";
    private final static String ORDER_ORIENTATION_ASC = " ASC";
    private final static String ORDER_ORIENTATION_DESC = " DESC";


    private GetGiftCertificateSQLBuilder() {
    }

    /**
     * Returns instance of the class (Singleton).
     *
     * @return Instance of {@link GetGiftCertificateSQLBuilder}.
     */
    public static GetGiftCertificateSQLBuilder getInstance() {
        return instance;
    }

    /**
     * Transforms parameters from request into SQL query and array of parameters.
     *
     * @param giftCertificateQueryParameter is {@link GetGiftCertificateQueryParameter} object with parameters from
     *                                      request.
     * @return {@link GiftCertificateSQL} object, that contains SQL query and array of parameters.
     */
    public GiftCertificateSQL build(GetGiftCertificateQueryParameter giftCertificateQueryParameter) {
        StringBuilder queryBuilder = new StringBuilder();
        List<String> conditionList = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        boolean whereIsUsed = false;

        String name = giftCertificateQueryParameter.getName();
        String description = giftCertificateQueryParameter.getDescription();
        String tagName = giftCertificateQueryParameter.getTagName();
        SortBy sortBy = giftCertificateQueryParameter.getSortBy();
        SortOrientation sortOrientation = giftCertificateQueryParameter.getSortOrientation();

        if (tagName != null) {
            whereIsUsed = true;
            queryBuilder.append(SELECT_WITH_TAG_NAME_SQL);
            conditionList.add(ADD_TAG_NAME_PARAM);
            params.add(tagName);
        } else {
            queryBuilder.append(SELECT_SQL);
        }

        if (name != null) {
            whereIsUsed = true;
            conditionList.add(ADD_NAME_SQL);
            params.add(name);
        }

        if (description != null) {
            whereIsUsed = true;
            conditionList.add(ADD_DESCRIPTION_SQL);
            params.add(description);
        }

        if (whereIsUsed) {
            queryBuilder.append(WHERE_SQL);
            queryBuilder.append(String.join(SPLIT_PARAM_STRING, conditionList));
            queryBuilder.append(CLOSE_WHERE_SQL);
        }

        if (sortBy != null) {
            queryBuilder.append(ORDER_BY_SQL);

            if (sortBy == SortBy.NAME) {
                queryBuilder.append(NAME_PARAM);
            } else if (sortBy == SortBy.DATE) {
                queryBuilder.append(LAST_UPDATE_DATE_PARAM);
            }
        }

        if (sortOrientation != null) {
            switch (sortOrientation) {
                case ASC: {
                    queryBuilder.append(ORDER_ORIENTATION_ASC);
                    break;
                }
                case DESC:
                    queryBuilder.append(ORDER_ORIENTATION_DESC);
                    break;
            }
        }

        return new GiftCertificateSQL(queryBuilder.toString(), params.toArray());
    }

}

