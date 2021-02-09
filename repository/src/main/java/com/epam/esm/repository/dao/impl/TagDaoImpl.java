package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.model.entity.*;
import com.epam.esm.repository.model.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link TagDao}. Provides methods to interact with Tags data from database.
 * Methods connect to database using {@link DataSource} and manipulate with data(save, edit, etc.).
 */
@Repository
public class TagDaoImpl implements TagDao {

    private static final int PAGE_NUMBER_OFFSET = 1;

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
     * @param page is page number
     * @param size is page size
     * @return List of all {@link Tag} entities from database.
     */
    @Override
    public List<Tag> getTags(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tag> tagQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = tagQuery.from(Tag.class);
        tagQuery.select(root);

        int itemsOffset = (page - PAGE_NUMBER_OFFSET) * size;
        return entityManager.createQuery(tagQuery).setFirstResult(itemsOffset).setMaxResults(size).getResultList();
    }

    /**
     * Connects to database and returns list of Tags linked to GiftCertificate in gift_tag table
     *
     * @param id is GiftCertificate
     * @return List of matched {@link Tag} entities from database.
     */
    @Override
    public List<Tag> getTagListByGiftCertificateID(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> tagQuery = criteriaBuilder.createQuery(Tag.class);

        Root<GiftCertificate> giftRoot = tagQuery.from(GiftCertificate.class);
        ListJoin<GiftCertificate, Tag> tagList = giftRoot.joinList(GiftCertificate_.TAG_LIST);
        tagQuery
                .select(tagList)
                .where(criteriaBuilder.equal(giftRoot.get(GiftCertificate_.ID), id));

        return entityManager.createQuery(tagQuery).getResultList();
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

        return entityManager.createQuery(tagQuery).getResultStream().findFirst();
    }

    public Tag getMostWidelyUsedTagFromUser(int userID) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> tagQuery = criteriaBuilder.createQuery(Tag.class);

        Root<User> userRoot = tagQuery.from(User.class);
        ListJoin<User, Order> orderList = userRoot.joinList(User_.ORDER_LIST);
        ListJoin<Order, GiftCertificate> giftList = orderList.joinList(Order_.GIFT_LIST);
        ListJoin<GiftCertificate, Tag> tagList = giftList.joinList(GiftCertificate_.TAG_LIST);

        Expression orderID = tagList.get(Order_.ID);
        tagQuery
                .select(tagList)
                .where(criteriaBuilder.equal(userRoot.get(User_.ID), userID))
                .groupBy(orderID)
                .orderBy(criteriaBuilder.desc(criteriaBuilder.count(orderID)));

        return entityManager.createQuery(tagQuery).setMaxResults(1).getSingleResult();
    }

}
