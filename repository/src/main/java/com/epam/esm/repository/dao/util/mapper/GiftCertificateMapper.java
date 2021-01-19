package com.epam.esm.repository.dao.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class is implementation of {@link RowMapper}, that links GiftCertificate with ResultSet.
 */
public final class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    private final static GiftCertificateMapper instance = new GiftCertificateMapper();

    private GiftCertificateMapper() {
    }

    /**
     * Returns instance of the class (Singleton).
     *
     * @return Instance of {@link GiftCertificateMapper}.
     */
    public static GiftCertificateMapper getInstance() {
        return instance;
    }

    /**
     * Links ResultSet with GiftCertificate entity
     *
     * @param rs     is {@link ResultSet} object from JDBC request.
     * @param rowNum is id of the row.
     * @return {@link GiftCertificate} entity from database.
     * @throws SQLException when something goes wrong.
     */
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getInt(ParamColumn.ID));
        giftCertificate.setName(rs.getString(ParamColumn.NAME));
        giftCertificate.setDescription(rs.getString(ParamColumn.DESCRIPTION));
        giftCertificate.setPrice(rs.getInt(ParamColumn.PRICE));
        giftCertificate.setDuration(rs.getInt(ParamColumn.DURATION));
        giftCertificate.setCreateDate(rs.getTimestamp(ParamColumn.CREATE_DATE).toInstant());
        giftCertificate.setLastUpdateDate(rs.getTimestamp(ParamColumn.LAST_UPDATE_DATE).toInstant());
        return giftCertificate;
    }

    private static class ParamColumn {
        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String DESCRIPTION = "description";
        private static final String PRICE = "price";
        private static final String DURATION = "duration";
        private static final String CREATE_DATE = "create_date";
        private static final String LAST_UPDATE_DATE = "last_update_date";
    }
}
