package com.epam.esm.repository.dao;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.util.FilteredGetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides methods to interact with GiftCertificate data from database.
 * Methods should connect to database and manipulate with data(create, read, update, delete).
 */
public interface GiftCertificateDAO {

    /**
     * Connects to database and deletes GiftCertificate with provided ID
     *
     * @param id is GiftCertificate ID value.
     */
    void deleteGiftCertificate(int id);

    /**
     * Connects to database and updates GiftCertificate.
     *
     * @param giftCertificate {@link GiftCertificate} Data object containing updated info
     * @return updated {@link GiftCertificate} entity
     */
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate, int id);

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
     * @param getGiftCertificateQueryParameter {@link GetGiftCertificateQueryParameter} Data object containing params for request
     * @return List of matched {@link GiftCertificate} entities from database.
     */
    List<GiftCertificate> getGiftCertificates(GetGiftCertificateQueryParameter getGiftCertificateQueryParameter);


    List<GiftCertificate> getGiftCertificates(FilteredGetGiftCertificateQueryParameter getGiftCertificateQueryParameter);

}
