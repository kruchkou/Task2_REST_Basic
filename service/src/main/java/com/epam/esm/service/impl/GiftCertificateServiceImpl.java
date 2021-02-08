package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.TagDAO;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.impl.DataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.exception.impl.GiftCertificateByParametersNotFoundException;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.util.mapper.EntityDtoGiftCertificateMapper;
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
    private static final String ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED = "0102404%d";

    /**
     * Error message when GiftCertificate wasn't found by parameters
     */
    private static final String NO_GIFT_CERTIFICATE_WITH_PARAMETERS_FOUND = "No certificate with parameters found";

    /**
     * Error code when GiftCertificate wasn't found by parameters
     */
    private static final String ERROR_CODE_GIFT_NOT_FOUND_FAILED = "0142404";

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
     * @throws GiftCertificateByParameterNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    @Transactional
    public void deleteCertificate(int id) {
        if (!giftCertificateDAO.getGiftCertificateByID(id).isPresent()) {
            throw new GiftCertificateByParameterNotFoundException(
                    String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }

        giftCertificateDAO.deleteGiftCertificate(id);
    }

    /**
     * Invokes DAO method to get GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be returned.
     * @return {@link GiftCertificateDto} object with GiftCertificate data.
     * @throws GiftCertificateByParameterNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    public GiftCertificateDto getGiftCertificateByID(int id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.getGiftCertificateByID(id);

        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(() -> new GiftCertificateByParameterNotFoundException(
                String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDtoGiftCertificateMapper.toDTO(giftCertificate);
    }

    /**
     * Invokes DAO method to update GiftCertificate with provided data.
     *
     * @param giftCertificateDTO is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with updated data.
     * @throws GiftCertificateByParameterNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDTO, int id) {
        if (!GiftCertificateValidator.validateForUpdate(giftCertificateDTO)) {
            throw new DataValidationException(
                    DATA_VALIDATION_EXCEPTION, ERROR_CODE_GIFT_VALIDATION_FAILED);
        }

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.getGiftCertificateByID(id);

        if (!optionalGiftCertificate.isPresent()) {
            throw new GiftCertificateByParameterNotFoundException(
                    String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }

        giftCertificateDTO.setId(id);

        List<Tag> tagList = createTagsIfNotFoundAndReturnAll(giftCertificateDTO.getTags());

        GiftCertificate giftCertificate = EntityDtoGiftCertificateMapper.toEntity(giftCertificateDTO);
        giftCertificate.setTagList(tagList);

        GiftCertificate updatedGiftCertificate = giftCertificateDAO.updateGiftCertificate(giftCertificate, id);

        return EntityDtoGiftCertificateMapper.toDTO(updatedGiftCertificate);
    }

    /**
     * Invokes DAO method to create GiftCertificate with provided data.
     *
     * @param giftCertificateDTO is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with created data.
     * @throws DataValidationException if data failed validation
     */
    @Override
    @Transactional
    public GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDTO) {
        if (!GiftCertificateValidator.validateForCreate(giftCertificateDTO)) {
            throw new DataValidationException(
                    DATA_VALIDATION_EXCEPTION, ERROR_CODE_GIFT_VALIDATION_FAILED);
        }

        List<Tag> tagList = createTagsIfNotFoundAndReturnAll(giftCertificateDTO.getTags());

        GiftCertificate giftCertificate = EntityDtoGiftCertificateMapper.toEntity(giftCertificateDTO);
        giftCertificate.setTagList(tagList);

        GiftCertificate newGiftCertificate = giftCertificateDAO.createGiftCertificate(giftCertificate);

        return EntityDtoGiftCertificateMapper.toDTO(newGiftCertificate);
    }

    /**
     * Invokes DAO method to get List of all GiftCertificates from database.
     *
     * @param page is {@link Page} object with page number and page size
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    @Override
    public List<GiftCertificateDto> getCertificates(Page page) {
        List<GiftCertificate> giftCertificateList = giftCertificateDAO.getGiftCertificates(
                page.getPage(), page.getSize());

        return EntityDtoGiftCertificateMapper.toDTO(giftCertificateList);
    }

    /**
     * Invokes DAO method to get List of all GiftCertificates that matches parameters
     *
     * @param giftCertificateQueryParameter is {@link GetGiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    @Override
    public List<GiftCertificateDto> getCertificates(GetGiftCertificateQueryParameter giftCertificateQueryParameter) {
        if (giftCertificateQueryParameter.isEmpty()) {
            return getCertificates(giftCertificateQueryParameter.getPage());
        }

        List<GiftCertificate> giftCertificateList = giftCertificateDAO.getGiftCertificates(giftCertificateQueryParameter);

        if (giftCertificateList.isEmpty()) {
            throw new GiftCertificateByParametersNotFoundException(
                    NO_GIFT_CERTIFICATE_WITH_PARAMETERS_FOUND, ERROR_CODE_GIFT_NOT_FOUND_FAILED);
        }

        return EntityDtoGiftCertificateMapper.toDTO(giftCertificateList);
    }

    private List<Tag> createTagsIfNotFoundAndReturnAll(List<String> tagNamesList) {
        if (tagNamesList == null) {
            return new ArrayList<>();
        }

        List<Tag> tagList = new ArrayList<>();

        tagNamesList.forEach(tagName -> {
            Optional<Tag> optionalTag = tagDAO.getTagByName(tagName);

            Tag tag = optionalTag.orElseGet(() -> tagDAO.createTag(tagName));
            tagList.add(tag);
        });

        return tagList;
    }

}
