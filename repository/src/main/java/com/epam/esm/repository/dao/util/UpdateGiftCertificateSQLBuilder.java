package com.epam.esm.repository.dao.util;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.util.GiftCertificateSQL;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is builder, that transforms parameters from UPDATE request into SQL query and array of parameters.
 */
public final class UpdateGiftCertificateSQLBuilder {

    private static final UpdateGiftCertificateSQLBuilder instance = new UpdateGiftCertificateSQLBuilder();

    private static final String SPLIT_PARAM_STRING = ", ";
    private static final String UPDATE_SQL = "UPDATE gift_certificate SET ";
    private static final String ADD_NAME_SQL = "name = ?";
    private static final String ADD_DESCRIPTION = "description = ?";
    private static final String ADD_PRICE = "price = ?";
    private static final String ADD_DURATION = "duration = ?";
    private static final String SET_ID_SQL = " WHERE id = ?";
    private static final String ADD_LAST_UPDATE_DATE = "last_update_date = ?";

    private UpdateGiftCertificateSQLBuilder() {
    }

    /**
     * Returns instance of the class (Singleton).
     *
     * @return Instance of {@link UpdateGiftCertificateSQLBuilder}.
     */
    public static UpdateGiftCertificateSQLBuilder getInstance() {
        return instance;
    }

    /**
     * Transforms parameters from request into SQL query and array of parameters.
     * Sets new update date if wasn't provided
     *
     * @param giftCertificate is {@link GiftCertificate} object with parameters from
     *                        request.
     * @return {@link GiftCertificateSQL} object, that contains SQL query and array of parameters.
     */
    public GiftCertificateSQL build(GiftCertificate giftCertificate) {
        List<String> conditionList = new ArrayList<>();
        List<Object> paramList = new ArrayList<>();

        String name = giftCertificate.getName();
        if (name != null) {
            conditionList.add(ADD_NAME_SQL);
            paramList.add(name);
        }

        String description = giftCertificate.getDescription();
        if (description != null) {
            conditionList.add(ADD_DESCRIPTION);
            paramList.add(description);
        }

        Integer price = giftCertificate.getPrice();
        if (price != null) {
            conditionList.add(ADD_PRICE);
            paramList.add(price);
        }

        Integer duration = giftCertificate.getDuration();
        if (duration != null) {
            conditionList.add(ADD_DURATION);
            paramList.add(duration);
        }

        Instant lastUpdateDate = giftCertificate.getLastUpdateDate();
        Timestamp currentTimestamp = lastUpdateDate == null ?
                Timestamp.from(Instant.now()) : Timestamp.from(lastUpdateDate);

        conditionList.add(ADD_LAST_UPDATE_DATE);
        paramList.add(currentTimestamp);

        Integer id = giftCertificate.getId();
        paramList.add(id);

        Object[] paramArray = paramList.toArray();

        String sqlParamQuery = String.join(SPLIT_PARAM_STRING, conditionList);
        String requestSql = UPDATE_SQL + sqlParamQuery + SET_ID_SQL;

        return new GiftCertificateSQL(requestSql, paramArray);
    }

}
