package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.util.GetGiftCertificateQueryHandler;
import com.epam.esm.repository.dao.util.GiftCertificateFieldUpdater;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return GetGiftCertificateQueryHandler.handle(entityManager, getGiftCertificateQueryParameter);
    }

}
