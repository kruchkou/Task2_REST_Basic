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

    private static final GetGiftCertificateSQLBuilder instance = new GetGiftCertificateSQLBuilder();

    private static final String SPLIT_PARAM_STRING = " AND ";

    private static final String SELECT_SQL = "SELECT * from gift_certificate gifts ";
    private static final String SELECT_WITH_TAG_NAME_SQL = "SELECT * from gift_certificate gifts " +
            "INNER JOIN gift_tag link ON gifts.id = link.gift " +
            "INNER JOIN tag tags ON link.tag = tags.id ";
    private static final String WHERE_SQL = "WHERE (";
    private static final String ADD_NAME_SQL = "gifts.name REGEXP ?";
    private static final String ADD_DESCRIPTION_SQL = "description REGEXP ?";
    private static final String ADD_TAG_NAME_PARAM = "tags.name = ?";
    private static final String CLOSE_WHERE_SQL = ")";
    private static final String ORDER_BY_SQL = " ORDER BY ";
    private static final String NAME_PARAM = "gifts.name";
    private static final String LAST_UPDATE_DATE_PARAM = "last_update_date";
    private static final String ORDER_ORIENTATION_ASC = " ASC";
    private static final String ORDER_ORIENTATION_DESC = " DESC";


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


        String tagName = giftCertificateQueryParameter.getTagName();
        if (tagName != null) {
            whereIsUsed = true;
            queryBuilder.append(SELECT_WITH_TAG_NAME_SQL);
            conditionList.add(ADD_TAG_NAME_PARAM);
            params.add(tagName);
        } else {
            queryBuilder.append(SELECT_SQL);
        }

        String name = giftCertificateQueryParameter.getName();
        if (name != null) {
            whereIsUsed = true;
            conditionList.add(ADD_NAME_SQL);
            params.add(name);
        }

        String description = giftCertificateQueryParameter.getDescription();
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

        SortBy sortBy = giftCertificateQueryParameter.getSortBy();
        if (sortBy != null) {
            queryBuilder.append(ORDER_BY_SQL);

            if (sortBy == SortBy.NAME) {
                queryBuilder.append(NAME_PARAM);
            } else if (sortBy == SortBy.DATE) {
                queryBuilder.append(LAST_UPDATE_DATE_PARAM);
            }
        }

        SortOrientation sortOrientation = giftCertificateQueryParameter.getSortOrientation();
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

