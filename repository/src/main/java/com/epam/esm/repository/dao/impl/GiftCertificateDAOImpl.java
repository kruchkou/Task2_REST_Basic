package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.util.mapper.GiftCertificateMapper;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.util.GiftCertificateSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link GiftCertificate}. Provides methods to interact with Tags data from database.
 * Methods connect to database using {@link DataSource} and manipulate with data(save, edit, etc.).
 */
@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    /**
     * An object of {@link GiftCertificateMapper}
     */
    private static final GiftCertificateMapper giftCertificateMapper = GiftCertificateMapper.getInstance();

    /**
     * Query for database to get all GiftCertificates
     */
    private static final String SELECT_ALL_SQL = "SELECT * FROM gift_certificate";

    /**
     * Query for database to get the GiftCertificate with provided id
     */
    private static final String SELECT_GIFT_BY_ID_SQL = "SELECT * FROM gift_certificate WHERE (id = ?)";

    /**
     * Query for database to delete a GiftCertificate with provided id
     */
    private static final String DELETE_SQL = "DELETE FROM gift_certificate WHERE id = ?";

    /**
     * Query for database to delete records from gift_tag table with provided gift ID
     */
    private static final String DELETE_FROM_GIFT_TAG_SQL = "DELETE FROM gift_tag WHERE (gift = ?)";

    /**
     * Query for database to create a GiftCertificate with provided data
     */
    private static final String CREATE_SQL = "INSERT INTO gift_certificate" +
            " (name, description, price, duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)";

    /**
     * Query for database to make record to gift_tag table that Gift with provided giftID have tag with provided
     */
    private static final String INSERT_INTO_GIFT_TAG_SQL = "INSERT INTO gift_tag(gift, tag) VALUES (?,?)";

    /**
     * Index of the first element from List.
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

    /**
     * An object of {@link JdbcTemplate}
     */
    private final JdbcTemplate jdbcTemplate;


    /**
     * Constructor that requires dataSource
     *
     * @param dataSource is {@link DataSource} object that manages connections
     */
    @Autowired
    public GiftCertificateDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Connects to database and add an new GiftCertificate.
     *
     * @param giftCertificate {@link GiftCertificate} giftCertificate is entity with data for creating GiftCertificate.
     * @return Created {@link GiftCertificate} entity from database
     */
    @Override
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final Timestamp CURRENT_TIMESTAMP = Timestamp.from(Instant.now());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(ParamColumn.NAME_PARAM_ID, giftCertificate.getName());
            ps.setString(ParamColumn.DESC_PARAM_ID, giftCertificate.getDescription());
            ps.setInt(ParamColumn.PRICE_PARAM_ID, giftCertificate.getPrice());
            ps.setInt(ParamColumn.DURATION_PARAM_ID, giftCertificate.getDuration());
            ps.setTimestamp(ParamColumn.CREATE_DATE_PARAM_ID, CURRENT_TIMESTAMP);
            ps.setTimestamp(ParamColumn.LAST_UPDATE_DATE_PARAM_ID, CURRENT_TIMESTAMP);
            return ps;
        }, keyHolder);

        int id = keyHolder.getKey().intValue();
        return getGiftCertificateByID(id).get();
    }

    /**
     * Connects to database and updates GiftCertificate.
     *
     * @param giftCertificateSQL {@link GiftCertificateSQL} Data object containing SQL string and params for request
     * @param id                 is GiftCertificate ID value.
     * @return updated {@link GiftCertificate} entity
     */
    @Override
    public GiftCertificate updateGiftCertificate(GiftCertificateSQL giftCertificateSQL, int id) {
        jdbcTemplate.update(giftCertificateSQL.getRequest(), giftCertificateSQL.getParams());

        return getGiftCertificateByID(id).get();
    }

    /**
     * Connects to database and delete records from gift_tag table with provided gift ID
     *
     * @param id is GiftCertificate ID value.
     */
    @Override
    public void deleteLinkWithTagsByID(int id) {
        jdbcTemplate.update(DELETE_FROM_GIFT_TAG_SQL, id);
    }

    /**
     * Connects to database and make record to gift_tag table that Gift with provided giftID have tag with provided
     * tagID
     *
     * @param giftID is GiftCertificate ID value.
     * @param tagID  is Tag ID value.
     */
    @Override
    public void insertGiftTag(int giftID, int tagID) {
        jdbcTemplate.update(INSERT_INTO_GIFT_TAG_SQL, giftID, tagID);
    }

    /**
     * Connects to database and deletes GiftCertificate with provided ID
     *
     * @param id is GiftCertificate ID value.
     */
    @Override
    public void deleteGiftCertificate(int id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    /**
     * Connects to database and returns GiftCertificate by ID.
     *
     * @param id is GiftCertificate ID value.
     * @return Optional of {@link GiftCertificate} entity from database.
     */
    @Override
    public Optional<GiftCertificate> getGiftCertificateByID(int id) {
        List<GiftCertificate> giftList = jdbcTemplate.query(SELECT_GIFT_BY_ID_SQL,
                new Object[]{id}, giftCertificateMapper);

        return giftList.isEmpty() ? Optional.empty() : Optional.of(giftList.get(FIRST_ELEMENT_INDEX));
    }

    /**
     * Connects to database and returns all GiftCertificates.
     *
     * @return List of all {@link GiftCertificate} entities from database.
     */
    @Override
    public List<GiftCertificate> getGiftCertificates() {
        return jdbcTemplate.query(SELECT_ALL_SQL, giftCertificateMapper);
    }

    /**
     * Connects to database and returns list of matching GiftCertificates
     *
     * @param giftCertificateSQL {@link GiftCertificateSQL} Data object containing SQL string and params for request
     * @return List of matched {@link GiftCertificate} entities from database.
     */
    @Override
    public List<GiftCertificate> getGiftCertificates(GiftCertificateSQL giftCertificateSQL) {
        return jdbcTemplate.query(
                giftCertificateSQL.getRequest(), giftCertificateSQL.getParams(), GiftCertificateMapper.getInstance());
    }

    /**
     * Static class that contains parameters of GiftCertificates table.
     */
    private static class ParamColumn {
        private static final int NAME_PARAM_ID = 1;
        private static final int DESC_PARAM_ID = 2;
        private static final int PRICE_PARAM_ID = 3;
        private static final int DURATION_PARAM_ID = 4;
        private static final int CREATE_DATE_PARAM_ID = 5;
        private static final int LAST_UPDATE_DATE_PARAM_ID = 6;
    }

}
