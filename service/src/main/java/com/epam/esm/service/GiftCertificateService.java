package com.epam.esm.service;

import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.model.dto.GiftCertificateDTO;

import java.util.List;

/**
 * Interface provides methods to interact with GiftCertificateDAO.
 * Methods should transforms received information into DAO-accepted data and invoke corresponding methods.
 */
public interface GiftCertificateService {

    /**
     * Invokes DAO method to delete GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be deleted.
     */
    void deleteCertificate(int id);

    /**
     * Invokes DAO method to get GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be returned.
     * @return {@link GiftCertificateDTO} object with GiftCertificate data.
     */
    GiftCertificateDTO getGiftCertificateByID(int id);

    /**
     * Invokes DAO method to create GiftCertificate with provided data.
     *
     * @param giftCertificateDTO is {@link GiftCertificateDTO} object with GiftCertificate data.
     * @return {@link GiftCertificateDTO} object with created data.
     */
    GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO);

    /**
     * Invokes DAO method to update GiftCertificate with provided data.
     *
     * @param giftCertificateDTO is {@link GiftCertificateDTO} object with GiftCertificate data.
     * @return {@link GiftCertificateDTO} object with updated data.
     */
    GiftCertificateDTO updateCertificate(GiftCertificateDTO giftCertificateDTO, int id);

    /**
     * Invokes DAO method to get List of all GiftCertificates from database.
     *
     * @return List of {@link GiftCertificateDTO} objects with GiftCertificate data.
     */
    List<GiftCertificateDTO> getCertificates();

    /**
     * Invokes DAO method to get List of all GiftCertificates that matches parameters
     *
     * @param giftCertificateQueryParameter is {@link GetGiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDTO} objects with GiftCertificate data.
     */
    List<GiftCertificateDTO> getCertificates(GetGiftCertificateQueryParameter giftCertificateQueryParameter);

}
