package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.util.GetGiftCertificateCriteriaBuilder;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link GiftCertificate}. Provides methods to interact with Tags data from database.
 * Methods connect to database using {@link DataSource} and manipulate with data(save, edit, etc.).
 */
@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    /**
     * Name of query to make record to gift_tag table that Gift with provided giftID have tag with provided
     */
    private static final String INSERT_INTO_GIFT_TAG_NAMED_QUERY = "insertIntoGiftTag";

    /**
     * Name of query to delete records from gift_tag table with provided gift ID
     */
    private static final String DELETE_FROM_GIFT_TAG_NAMED_QUERY = "deleteLinkWithTagsByGiftID";

    /**
     * Gift ID parameter
     */
    private static final String GIFT_ID_PARAM = "giftID";

    /**
     * Tag ID parameter
     */
    private static final String TAG_ID_PARAM = "tagID";


    /**
     * An object of {@link EntityManager} that is being injected.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Connects to database and add an new GiftCertificate.
     *
     * @param giftCertificate {@link GiftCertificate} giftCertificate is entity with data for creating GiftCertificate.
     * @return Created {@link GiftCertificate} entity from database
     */
    @Override
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        giftCertificate.setCreateDate(currentLocalDateTime);
        giftCertificate.setLastUpdateDate(currentLocalDateTime);

        entityManager.persist(giftCertificate);
        entityManager.detach(giftCertificate);

        return giftCertificate;
    }

    /**
     * Connects to database and updates GiftCertificate.
     *
     * @param giftCertificate {@link GiftCertificate} Entity containing data to be updated
     * @return updated {@link GiftCertificate} entity
     */

    @Override
    public GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate, int id) {
        GiftCertificate oldGiftCertificate = entityManager.find(GiftCertificate.class, id);

        Integer price = giftCertificate.getPrice();
        if (price != null) {
            oldGiftCertificate.setPrice(price);
        }

        Integer duration = giftCertificate.getDuration();
        if (duration != null) {
            oldGiftCertificate.setDuration(duration);
        }

        String name = giftCertificate.getDescription();
        if (name != null) {
            oldGiftCertificate.setName(name);
        }

        String description = giftCertificate.getDescription();
        if (description != null) {
            oldGiftCertificate.setDescription(description);
        }

        List<Tag> tagList = giftCertificate.getTagList();
        if (tagList != null) {
            oldGiftCertificate.setTagList(tagList);
        }

        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        oldGiftCertificate.setLastUpdateDate(currentLocalDateTime);

        entityManager.merge(oldGiftCertificate);
        entityManager.detach(oldGiftCertificate);

        return oldGiftCertificate;
    }

    /**
     * Connects to database and delete records from gift_tag table with provided gift ID
     *
     * @param id is GiftCertificate ID value.
     */
    @Override
    public void deleteLinkWithTagsByID(int id) {
        Query query = entityManager.createNamedQuery(DELETE_FROM_GIFT_TAG_NAMED_QUERY);
        query.setParameter(GIFT_ID_PARAM, id);

        query.executeUpdate();
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
        Query query = entityManager.createNamedQuery(INSERT_INTO_GIFT_TAG_NAMED_QUERY);
        query.setParameter(GIFT_ID_PARAM, giftID);
        query.setParameter(TAG_ID_PARAM, tagID);

        query.executeUpdate();
    }

    /**
     * Connects to database and deletes GiftCertificate with provided ID
     *
     * @param id is GiftCertificate ID value.
     */
    @Override
    public void deleteGiftCertificate(int id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);

        entityManager.remove(giftCertificate);
    }

    /**
     * Connects to database and returns GiftCertificate by ID.
     *
     * @param id is GiftCertificate ID value.
     * @return Optional of {@link GiftCertificate} entity from database.
     */
    @Override
    public Optional<GiftCertificate> getGiftCertificateByID(int id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);

        return Optional.ofNullable(giftCertificate);
    }

    /**
     * Connects to database and returns all GiftCertificates.
     *
     * @return List of all {@link GiftCertificate} entities from database.
     */
    @Override
    public List<GiftCertificate> getGiftCertificates() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<GiftCertificate> giftQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = giftQuery.from(GiftCertificate.class);
        giftQuery.select(root);

        return entityManager.createQuery(giftQuery).getResultList();
    }

    /**
     * Connects to database and returns list of matching GiftCertificates
     *
     * @param getGiftCertificateQueryParameter {@link GetGiftCertificateQueryParameter} Data object containing params
     *                                         for request
     * @return List of matched {@link GiftCertificate} entities from database.
     */
    @Override
    public List<GiftCertificate> getGiftCertificates(GetGiftCertificateQueryParameter getGiftCertificateQueryParameter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = GetGiftCertificateCriteriaBuilder.getInstance().build(criteriaBuilder,
                getGiftCertificateQueryParameter);

        return entityManager.createQuery(query).getResultList();
    }

}
