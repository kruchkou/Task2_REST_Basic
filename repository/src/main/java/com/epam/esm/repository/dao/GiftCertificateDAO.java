package com.epam.esm.repository.dao;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.util.GiftCertificateSQL;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides methods to interact with GiftCertificate data from database.
 * Methods should connect to database and manipulate with data(save, edit, etc.).
 */
public interface GiftCertificateDAO {

    /**
     * Connects to database and deletes GiftCertificate with provided ID
     *
     * @param id is GiftCertificate ID value.
     */
    void deleteGiftCertificate(int id);

    /**
     * Connects to database and delete records from gift_tag table with provided gift ID
     *
     * @param id is GiftCertificate ID value.
     */
    void deleteLinkWithTagsByID(int id);

    /**
     * Connects to database and make record to gift_tag table that Gift with provided giftID have tag with provided
     * tagID
     *
     * @param giftID is GiftCertificate ID value.
     * @param tagID  is Tag ID value.
     */
    void insertGiftTag(int giftID, int tagID);

    /**
     * Connects to database and updates GiftCertificate.
     *
     * @param giftCertificateSQL {@link GiftCertificateSQL} Data object containing SQL string and params for request
     * @param id                 is GiftCertificate ID value.
     * @return updated {@link GiftCertificate} entity
     */
    GiftCertificate updateGiftCertificate(GiftCertificateSQL giftCertificateSQL, int id);

    /**
     * Connects to database and add an new GiftCertificate.
     *
     * @param giftCertificate {@link GiftCertificate} giftCertificate is entity with data for creating GiftCertificate.
     * @return Created {@link GiftCertificate} entity from database
     */
    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate);

    /**
     * Connects to database and returns GiftCertificate by ID.
     *
     * @param id is GiftCertificate ID value.
     * @return Optional of {@link GiftCertificate} entity from database.
     */
    Optional<GiftCertificate> getGiftCertificateByID(int id);

    /**
     * Connects to database and returns all GiftCertificates.
     *
     * @return List of all {@link GiftCertificate} entities from database.
     */
    List<GiftCertificate> getGiftCertificates();

    /**
     * Connects to database and returns list of matching GiftCertificates
     *
     * @param giftCertificateSQL {@link GiftCertificateSQL} Data object containing SQL string and params for request
     * @return List of matched {@link GiftCertificate} entities from database.
     */
    List<GiftCertificate> getGiftCertificates(GiftCertificateSQL giftCertificateSQL);

}
