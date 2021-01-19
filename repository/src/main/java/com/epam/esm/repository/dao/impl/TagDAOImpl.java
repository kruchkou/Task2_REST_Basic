package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.TagDAO;
import com.epam.esm.repository.dao.util.mapper.TagMapper;
import com.epam.esm.repository.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link TagDAO}. Provides methods to interact with Tags data from database.
 * Methods connect to database using {@link DataSource} and manipulate with data(save, edit, etc.).
 */
@Repository
public class TagDAOImpl implements TagDAO {

    /**
     * An object of {@link JdbcTemplate}
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * An object of {@link TagMapper}
     */
    private final static TagMapper tagMapper = TagMapper.getInstance();

    /**
     * Query for database to create a tag with provided name
     */
    private final static String CREATE_SQL = "INSERT INTO tag(name) VALUES (?)";

    /**
     * Query for database to delete a tag with provided id
     */
    private final static String DELETE_SQL = "DELETE FROM tag WHERE id = ?";

    /**
     * Query for database to get the tag with provided id
     */
    private final static String GET_TAG_BY_ID_SQL = "SELECT * FROM tag WHERE id = ?";

    /**
     * Query for database to get all tags
     */
    private final static String GET_TAGS_SQL = "SELECT * FROM tag";

    /**
     * Query for database to get the tags that linked to a gift with provided id
     */
    private final static String SELECT_BY_GIFT_ID_SQL = "SELECT * FROM tag tags " +
            "INNER JOIN gift_tag link ON tags.id = link.tag " +
            "WHERE (link.gift = ?)";

    /**
     * Query for database to get the tags that linked to a gift with provided name
     */
    private final static String SELECT_BY_TAG_NAME_SQL = "SELECT * FROM tag WHERE (name = ?)";

    /**
     * Index of the first element from List.
     */
    private final static int FIRST_ELEMENT_INDEX = 0;

    /**
     * Constructor that requires dataSource
     *
     * @param dataSource is {@link DataSource} object that manages connections
     */
    @Autowired
    public TagDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Connects to database and add an new Tag.
     *
     * @param name is Tag name value
     * @return Created {@link Tag} entity from database
     */
    @Override
    public Tag createTag(String name) {
        jdbcTemplate.update(CREATE_SQL, name);

        return getTagByName(name).get();
    }

    /**
     * Connects to database and deletes Tag with provided ID
     *
     * @param id is Tag ID value.
     */
    @Override
    public void deleteTag(int id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    /**
     * Connects to database and returns Tag by ID.
     *
     * @param id is Tag ID value.
     * @return Optional of {@link Tag} entity from database.
     */
    @Override
    public Optional<Tag> getTagByID(int id) {
        final int FIRST_ELEMENT_INDEX = 0;

        List<Tag> tagList = jdbcTemplate.query(GET_TAG_BY_ID_SQL,
                new Object[]{id}, tagMapper);

        return tagList.isEmpty() ? Optional.empty() : Optional.of(tagList.get(FIRST_ELEMENT_INDEX));
    }

    /**
     * Connects to database and returns all Tags.
     *
     * @return List of all {@link Tag} entities from database.
     */
    @Override
    public List<Tag> getTags() {
        return jdbcTemplate.query(GET_TAGS_SQL, tagMapper);
    }

    /**
     * Connects to database and returns list of Tags linked to GiftCertificate in gift_tag table
     *
     * @param id is GiftCertificate
     * @return List of matched {@link Tag} entities from database.
     */
    @Override
    public List<Tag> getTagListByGiftCertificateID(int id) {
        return jdbcTemplate.query(SELECT_BY_GIFT_ID_SQL, new Object[]{id}, tagMapper);
    }

    /**
     * Connects to database and returns Tag by name.
     *
     * @param name is Tag name value.
     * @return Optional of {@link Tag} entity from database.
     */
    @Override
    public Optional<Tag> getTagByName(String name) {
        List<Tag> tagList = jdbcTemplate.query(SELECT_BY_TAG_NAME_SQL,
                new Object[]{name}, tagMapper);

        return tagList.isEmpty() ? Optional.empty() : Optional.of(tagList.get(FIRST_ELEMENT_INDEX));
    }
}
