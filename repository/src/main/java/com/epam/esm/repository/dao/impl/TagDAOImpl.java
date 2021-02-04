package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.TagDAO;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.GiftCertificate_;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.entity.Tag_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
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
     * Query for database to delete records from gift_tag table with provided gift ID.
     */
    private static final String GET_TAG_LIST_BY_GIFT_ID_NAMED_QUERY = "getTagListByGiftID";

    /**
     * Gift ID parameter.
     */
    private static final String GIFT_ID_PARAM = "giftID";


    /**
     * An object of {@link EntityManager} that is being injected.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Connects to database and add an new Tag.
     *
     * @param name is Tag name value
     * @return Created {@link Tag} entity from database
     */
    @Override
    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);

        entityManager.persist(tag);
        entityManager.detach(tag);

        return tag;
    }

    /**
     * Connects to database and deletes Tag with provided ID
     *
     * @param id is Tag ID value.
     */
    @Override
    public void deleteTag(int id) {
        Tag tag = entityManager.find(Tag.class, id);

        entityManager.remove(tag);
    }

    /**
     * Connects to database and returns Tag by ID.
     *
     * @param id is Tag ID value.
     * @return Optional of {@link Tag} entity from database.
     */
    @Override
    public Optional<Tag> getTagByID(int id) {
        Tag tag = entityManager.find(Tag.class, id);

        return Optional.ofNullable(tag);
    }

    /**
     * Connects to database and returns all Tags.
     *
     * @return List of all {@link Tag} entities from database.
     */
    @Override
    public List<Tag> getTags() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tag> tagQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = tagQuery.from(Tag.class);
        tagQuery.select(root);

        return entityManager.createQuery(tagQuery).getResultList();
    }

    /**
     * Connects to database and returns list of Tags linked to GiftCertificate in gift_tag table
     *
     * @param id is GiftCertificate
     * @return List of matched {@link Tag} entities from database.
     */
    @Override
    public List<Tag> getTagListByGiftCertificateID(int id) {
        Query query = entityManager.createNamedQuery(GET_TAG_LIST_BY_GIFT_ID_NAMED_QUERY);
        query.setParameter(GIFT_ID_PARAM, id);

        return query.getResultList();
    }

    /**
     * Connects to database and returns Tag by name.
     *
     * @param name is Tag name value.
     * @return Optional of {@link Tag} entity from database.
     */
    @Override
    public Optional<Tag> getTagByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tag> tagQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = tagQuery.from(Tag.class);
        tagQuery.select(root).where(criteriaBuilder.equal(root.get(Tag_.NAME), name));

        List<Tag> tagList = entityManager.createQuery(tagQuery).getResultList();

        Optional<Tag> tag;
        if (tagList.isEmpty()) {
            tag = Optional.empty();
        } else {
            tag = Optional.of(tagList.get(0));
            //entityManager.detach(tag);
        }
        return tag;
    }
}
