package com.epam.esm.service;

import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.model.dto.GiftCertificateDto;

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
     * @return {@link GiftCertificateDto} object with GiftCertificate data.
     */
    GiftCertificateDto getGiftCertificateByID(int id);

    /**
     * Invokes DAO method to create GiftCertificate with provided data.
     *
     * @param giftCertificateDTO is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with created data.
     */
    GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDTO);

    /**
     * Invokes DAO method to update GiftCertificate with provided data.
     *
     * @param giftCertificateDTO is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with updated data.
     */
    GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDTO, int id);

    /**
     * Invokes DAO method to get List of all GiftCertificates from database.
     *
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    List<GiftCertificateDto> getCertificates();

    /**
     * Invokes DAO method to get List of all GiftCertificates that matches parameters
     *
     * @param giftCertificateQueryParameter is {@link GetGiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    List<GiftCertificateDto> getCertificates(GetGiftCertificateQueryParameter giftCertificateQueryParameter);

}
