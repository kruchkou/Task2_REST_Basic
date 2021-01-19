package com.epam.esm.repository.dao.util.mapper;

import com.epam.esm.repository.model.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class is implementation of {@link RowMapper}, that links Tag entity with ResultSet.
 */
public final class TagMapper implements RowMapper<Tag> {

    private final static TagMapper instance = new TagMapper();

    private TagMapper() {
    }

    /**
     * Returns instance of the class (Singleton).
     *
     * @return Instance of {@link TagMapper}.
     */
    public static TagMapper getInstance() {
        return instance;
    }


    /**
     * Links ResultSet with GiftCertificate entity
     *
     * @param rs     is {@link ResultSet} object from JDBC request.
     * @param rowNum is id of the row.
     * @return {@link Tag} entity from database.
     * @throws SQLException when something goes wrong.
     */
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getInt(ParamColumn.ID));
        tag.setName(rs.getString(ParamColumn.NAME));

        return tag;
    }

    private static class ParamColumn {
        private static final String ID = "id";
        private static final String NAME = "name";
    }
}
