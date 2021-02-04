package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.TagDAO;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.impl.GiftCertificateDataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateNotFoundException;
import com.epam.esm.service.model.dto.GiftCertificateDTO;
import com.epam.esm.service.util.mapper.EntityDTOGiftCertificateMapper;
import com.epam.esm.service.util.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link GiftCertificateService}. Interface provides methods to interact with GiftCertificateDAO.
 * Methods should transforms received information into DAO-accepted data and invoke corresponding methods.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    /**
     * Error message when GiftCertificate wasn't found by id
     */
    private static final String NO_GIFT_CERTIFICATE_WITH_ID_FOUND = "No certificate with id: %d found";

    /**
     * Format string to provide info by what id GiftCertificate wasn't found.
     */
    private static final String NOT_FOUND_BY_ID_PARAMETER = "id: %d";

    /**
     * Error code when GiftCertificate wasn't found by id
     */
    private static final String ERROR_CODE_GIFT_NOT_FOUND_FAILED = "0102404%d";

    /**
     * Error message when data failed validation
     */
    private static final String DATA_VALIDATION_EXCEPTION = "Data didn't passed validation";

    /**
     * Error code when data failed validation
     */
    private static final String ERROR_CODE_GIFT_VALIDATION_FAILED = "0101";

    /**
     * An object of {@link GiftCertificateDAO}
     */
    private final GiftCertificateDAO giftCertificateDAO;

    /**
     * An object of {@link TagDAO}
     */
    private final TagDAO tagDAO;

    /**
     * Public constructor that receives giftCertificateDAO and tagDAO
     *
     * @param giftCertificateDAO is {@link GiftCertificateDAO} interface providing DAO methods.
     * @param tagDAO             is {@link TagDAO} interface providing DAO methods.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    /**
     * Invokes DAO method to delete GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be deleted.
     * @throws GiftCertificateNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    @Transactional
    public void deleteCertificate(int id) {
        if (!giftCertificateDAO.getGiftCertificateByID(id).isPresent()) {
            throw new GiftCertificateNotFoundException(
                    String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_GIFT_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }

        giftCertificateDAO.deleteGiftCertificate(id);
    }

    /**
     * Invokes DAO method to get GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be returned.
     * @return {@link GiftCertificateDTO} object with GiftCertificate data.
     * @throws GiftCertificateNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    public GiftCertificateDTO getGiftCertificateByID(int id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.getGiftCertificateByID(id);

        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(() -> new GiftCertificateNotFoundException(
                String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                String.format(ERROR_CODE_GIFT_NOT_FOUND_FAILED, id),
                String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDTOGiftCertificateMapper.toDTO(giftCertificate);
    }

    /**
     * Invokes DAO method to update GiftCertificate with provided data.
     *
     * @param giftCertificateDTO is {@link GiftCertificateDTO} object with GiftCertificate data.
     * @return {@link GiftCertificateDTO} object with updated data.
     * @throws GiftCertificateNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    @Transactional
    public GiftCertificateDTO updateCertificate(GiftCertificateDTO giftCertificateDTO, int id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.getGiftCertificateByID(id);

        if (!optionalGiftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(
                    String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_GIFT_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }

        giftCertificateDTO.setId(id);

        GiftCertificate giftCertificate = EntityDTOGiftCertificateMapper.toEntity(giftCertificateDTO);

        giftCertificateDAO.deleteLinkWithTagsByID(id);
        createTagsIfNotFoundAndInsert(id, giftCertificateDTO.getTagNames());

        GiftCertificate updatedGiftCertificate = giftCertificateDAO.updateGiftCertificate(giftCertificate, id);

        return EntityDTOGiftCertificateMapper.toDTO(updatedGiftCertificate);
    }

    /**
     * Invokes DAO method to create GiftCertificate with provided data.
     *
     * @param giftCertificateDTO is {@link GiftCertificateDTO} object with GiftCertificate data.
     * @return {@link GiftCertificateDTO} object with created data.
     * @throws GiftCertificateDataValidationException if data failed validation
     */
    @Override
    @Transactional
    public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO) {

        if (!GiftCertificateValidator.validateForCreate(giftCertificateDTO)) {
            throw new GiftCertificateDataValidationException(
                    DATA_VALIDATION_EXCEPTION, ERROR_CODE_GIFT_VALIDATION_FAILED);
        }

        GiftCertificate giftCertificate = EntityDTOGiftCertificateMapper.toEntity(giftCertificateDTO);

        GiftCertificate newGiftCertificate = giftCertificateDAO.createGiftCertificate(giftCertificate);
        createTagsIfNotFoundAndInsert(newGiftCertificate.getId(), giftCertificateDTO.getTagNames());

        newGiftCertificate = giftCertificateDAO.getGiftCertificateByID(newGiftCertificate.getId()).get(); //ДЕЙСТВИЕ
        // СТАНОВИТСЯ ЛИШНИМ ИЗ-ЗА ТЭГОВ

        return EntityDTOGiftCertificateMapper.toDTO(newGiftCertificate);
    }

    /**
     * Invokes DAO method to get List of all GiftCertificates from database.
     *
     * @return List of {@link GiftCertificateDTO} objects with GiftCertificate data.
     */
    @Override
    public List<GiftCertificateDTO> getCertificates() {
        List<GiftCertificate> giftCertificateList = giftCertificateDAO.getGiftCertificates();

        return EntityDTOGiftCertificateMapper.toDTO(giftCertificateList);
    }

    /**
     * Invokes DAO method to get List of all GiftCertificates that matches parameters
     *
     * @param giftCertificateQueryParameter is {@link GetGiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDTO} objects with GiftCertificate data.
     */
    @Override
    public List<GiftCertificateDTO> getCertificates(GetGiftCertificateQueryParameter giftCertificateQueryParameter) {
        if (giftCertificateQueryParameter.isEmpty()) {
            return getCertificates();
        }

        List<GiftCertificate> giftCertificateList = giftCertificateDAO.getGiftCertificates(giftCertificateQueryParameter);

        return EntityDTOGiftCertificateMapper.toDTO(giftCertificateList);
    }

    private void createTagsIfNotFoundAndInsert(int giftID, List<String> tagNamesList) { //ВЫЗЫВАЕТСЯ ВНЕ ТРАНЗАКЦИИ!!
        if (tagNamesList != null) {
            tagNamesList.forEach(tagName -> {
                Optional<Tag> optionalTag = tagDAO.getTagByName(tagName);
                Tag tag = optionalTag.orElseGet(() -> tagDAO.createTag(tagName));

                giftCertificateDAO.insertGiftTag(giftID, tag.getId());
            });
        }
    }

}
