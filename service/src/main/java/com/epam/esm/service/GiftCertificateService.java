package com.epam.esm.service;

import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface provides methods to interact with GiftCertificateRepository.
 * Methods should transforms received information into Repository-accepted data and invoke corresponding methods.
 */
public interface GiftCertificateService {

    /**
     * Invokes Repository method to delete GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be deleted.
     */
    void deleteCertificate(int id);

    /**
     * Invokes Repository method to get GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be returned.
     * @return {@link GiftCertificateDto} object with GiftCertificate data.
     */
    GiftCertificateDto getGiftCertificateByID(int id);

    /**
     * Invokes Repository method to create GiftCertificate with provided data.
     *
     * @param giftCertificateDto is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with created data.
     */
    GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Invokes Repository method to update GiftCertificate with provided data.
     *
     * @param giftCertificateDto is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with updated data.
     */
    GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDto, int id);

    /**
     * Invokes Repository method to get List of all GiftCertificates from database.
     *
     * @param pageable is {@link Pageable} object with page number and page size
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    List<GiftCertificateDto> getCertificates(Pageable pageable);

    /**
     * Invokes Repository method to get List of all GiftCertificates that matches parameters
     *
     * @param giftCertificateQueryParameter is {@link GetGiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    List<GiftCertificateDto> getCertificates(GetGiftCertificateQueryParameter giftCertificateQueryParameter,
                                             Pageable pageable);

    /**
     * Invokes Repository method to get List of all GiftCertificates that linked with Order by it's id
     *
     * @param id is id of Order.
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    List<GiftCertificateDto> getCertificateListByOrderID(int id);

}
