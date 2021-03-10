package com.epam.esm.service;

import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.model.dto.GiftCertificateDto;

import java.util.List;

/**
 * Interface provides methods to interact with GiftCertificateDao.
 * Methods should transforms received information into Dao-accepted data and invoke corresponding methods.
 */
public interface GiftCertificateService {

    /**
     * Invokes Dao method to delete GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be deleted.
     */
    void deleteCertificate(int id);

    /**
     * Invokes Dao method to get GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be returned.
     * @return {@link GiftCertificateDto} object with GiftCertificate data.
     */
    GiftCertificateDto getGiftCertificateByID(int id);

    /**
     * Invokes Dao method to create GiftCertificate with provided data.
     *
     * @param giftCertificateDto is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with created data.
     */
    GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Invokes Dao method to update GiftCertificate with provided data.
     *
     * @param giftCertificateDto is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with updated data.
     */
    GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDto, int id);

    /**
     * Invokes Dao method to get List of all GiftCertificates from database.
     *
     * @param page is {@link Page} object with page number and page size
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    List<GiftCertificateDto> getCertificates(Page page);

    /**
     * Invokes Dao method to get List of all GiftCertificates that matches parameters
     *
     * @param giftCertificateQueryParameter is {@link GetGiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    List<GiftCertificateDto> getCertificates(GetGiftCertificateQueryParameter giftCertificateQueryParameter);

    /**
     * Invokes Dao method to get List of all GiftCertificates that linked with Order by it's id
     *
     * @param id is id of Order.
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    List<GiftCertificateDto> getCertificateListByOrderID(int id);

}
