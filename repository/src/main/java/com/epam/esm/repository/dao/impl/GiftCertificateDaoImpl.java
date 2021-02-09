package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.util.GetGiftCertificateQueryHandler;
import com.epam.esm.repository.dao.util.GiftCertificateFieldUpdater;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.Order_;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
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
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final int PAGE_NUMBER_OFFSET = 1;

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

        GiftCertificateFieldUpdater.updateFields(oldGiftCertificate, giftCertificate);

        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        oldGiftCertificate.setLastUpdateDate(currentLocalDateTime);

        entityManager.merge(oldGiftCertificate);

        return oldGiftCertificate;
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
     * @param page is page number
     * @param size is page size
     * @return List of all {@link GiftCertificate} entities from database.
     */
    @Override
    public List<GiftCertificate> getGiftCertificates(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<GiftCertificate> giftQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = giftQuery.from(GiftCertificate.class);
        giftQuery.select(root);
        int itemsOffset = (page - PAGE_NUMBER_OFFSET) * size;
        return entityManager.createQuery(giftQuery).setFirstResult(itemsOffset).setMaxResults(size).getResultList();
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
        return GetGiftCertificateQueryHandler.handle(entityManager, getGiftCertificateQueryParameter);
    }

    /**
     * Connects to database and returns list of GiftCertificates linked to Order in order_gift table
     *
     * @param id is Order
     * @return List of matched {@link Order} entities from database.
     */
    @Override
    public List<GiftCertificate> getGiftCertificateListByOrderID(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> giftCertificateQuery = criteriaBuilder.createQuery(GiftCertificate.class);

        Root<Order> giftRoot = giftCertificateQuery.from(Order.class);
        ListJoin<Order, GiftCertificate> giftCertificateList = giftRoot.joinList(Order_.GIFT_LIST);
        giftCertificateQuery
                .select(giftCertificateList)
                .where(criteriaBuilder.equal(giftRoot.get(Order_.ID), id));

        return entityManager.createQuery(giftCertificateQuery).getResultList();
    }

}
