package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.OrderDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.impl.DataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.exception.impl.OrderNotFoundException;
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
 * Implementation of {@link GiftCertificateService}. Interface provides methods to interact with GiftCertificateDao.
 * Methods should transforms received information into Dao-accepted data and invoke corresponding methods.
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
     * Error message when data failed validation
     */
    private static final String DATA_VALIDATION_EXCEPTION = "Data didn't passed validation";

    /**
     * Error code when data failed validation
     */
    private static final String ERROR_CODE_GIFT_VALIDATION_FAILED = "0101";

    /**
     * Error message when Order wasn't found by id
     */
    private static final String NO_ORDER_WITH_ID_FOUND = "No order with id: %d found";

    /**
     * Error code when Order wasn't found by id
     */
    private static final String ERROR_CODE_ORDER_BY_ID_NOT_FOUND_FAILED = "0402404%d";

    /**
     * An object of {@link GiftCertificateDao}
     */
    private final GiftCertificateDao giftCertificateDao;

    /**
     * An object of {@link TagDao}
     */
    private final TagDao tagDao;
    private final OrderDao orderDao;

    /**
     * Public constructor that receives giftCertificateDao and tagDao
     *
     * @param giftCertificateDao is {@link GiftCertificateDao} interface providing Dao methods.
     * @param tagDao             is {@link TagDao} interface providing Dao methods.
     * @param orderDao           is {@link OrderDao} interface providing Dao methods.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, OrderDao orderDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.orderDao = orderDao;
    }

    /**
     * Invokes Dao method to delete GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be deleted.
     * @throws GiftCertificateByParameterNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    @Transactional
    public void deleteCertificate(int id) {
        if (!giftCertificateDao.getGiftCertificateByID(id).isPresent()) {
            throw new GiftCertificateByParameterNotFoundException(
                    String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }

        giftCertificateDao.deleteGiftCertificate(id);
    }

    /**
     * Invokes Dao method to get GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be returned.
     * @return {@link GiftCertificateDto} object with GiftCertificate data.
     * @throws GiftCertificateByParameterNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    public GiftCertificateDto getGiftCertificateByID(int id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.getGiftCertificateByID(id);

        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(() -> new GiftCertificateByParameterNotFoundException(
                String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDtoGiftCertificateMapper.toDto(giftCertificate);
    }

    /**
     * Invokes Dao method to update GiftCertificate with provided data.
     *
     * @param giftCertificateDto is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with updated data.
     * @throws GiftCertificateByParameterNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto giftCertificateDto, int id) {
        if (!GiftCertificateValidator.validateForUpdate(giftCertificateDto)) {
            throw new DataValidationException(
                    DATA_VALIDATION_EXCEPTION, ERROR_CODE_GIFT_VALIDATION_FAILED);
        }

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.getGiftCertificateByID(id);

        if (!optionalGiftCertificate.isPresent()) {
            throw new GiftCertificateByParameterNotFoundException(
                    String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }

        giftCertificateDto.setId(id);

        List<Tag> tagList = createTagsIfNotFoundAndReturnAll(giftCertificateDto.getTags());

        GiftCertificate giftCertificate = EntityDtoGiftCertificateMapper.toEntity(giftCertificateDto);
        giftCertificate.setTagList(tagList);

        GiftCertificate updatedGiftCertificate = giftCertificateDao.updateGiftCertificate(giftCertificate, id);

        return EntityDtoGiftCertificateMapper.toDto(updatedGiftCertificate);
    }

    /**
     * Invokes Dao method to create GiftCertificate with provided data.
     *
     * @param giftCertificateDto is {@link GiftCertificateDto} object with GiftCertificate data.
     * @return {@link GiftCertificateDto} object with created data.
     * @throws DataValidationException if data failed validation
     */
    @Override
    @Transactional
    public GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto) {
        if (!GiftCertificateValidator.validateForCreate(giftCertificateDto)) {
            throw new DataValidationException(
                    DATA_VALIDATION_EXCEPTION, ERROR_CODE_GIFT_VALIDATION_FAILED);
        }

        List<Tag> tagList = createTagsIfNotFoundAndReturnAll(giftCertificateDto.getTags());

        GiftCertificate giftCertificate = EntityDtoGiftCertificateMapper.toEntity(giftCertificateDto);
        giftCertificate.setTagList(tagList);

        GiftCertificate newGiftCertificate = giftCertificateDao.createGiftCertificate(giftCertificate);

        return EntityDtoGiftCertificateMapper.toDto(newGiftCertificate);
    }

    /**
     * Invokes Dao method to get List of all GiftCertificates from database.
     *
     * @param page is {@link Page} object with page number and page size
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    @Override
    public List<GiftCertificateDto> getCertificates(Page page) {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.getGiftCertificates(
                page.getPage(), page.getSize());

        return EntityDtoGiftCertificateMapper.toDto(giftCertificateList);
    }

    /**
     * Invokes Dao method to get List of all GiftCertificates that matches parameters
     *
     * @param giftCertificateQueryParameter is {@link GetGiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    @Override
    public List<GiftCertificateDto> getCertificates(GetGiftCertificateQueryParameter giftCertificateQueryParameter) {
        if (giftCertificateQueryParameter.isEmpty()) {
            return getCertificates(giftCertificateQueryParameter.getPage());
        }

        List<GiftCertificate> giftCertificateList = giftCertificateDao.getGiftCertificates(giftCertificateQueryParameter);

        return EntityDtoGiftCertificateMapper.toDto(giftCertificateList);
    }

    /**
     * Invokes Dao method to get List of all GiftCertificates that linked with Order by it's id
     *
     * @param id is id of Order.
     * @return List of {@link GiftCertificateDto} objects with giftCertificate data.
     * @throws OrderNotFoundException if no Certificate with id found
     */
    @Override
    public List<GiftCertificateDto> getCertificateListByOrderID(int id) {
        if (!orderDao.getOrder(id).isPresent()) {
            throw new OrderNotFoundException(
                    String.format(NO_ORDER_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_ORDER_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }
        List<GiftCertificate> giftCertificateList = giftCertificateDao.getGiftCertificateListByOrderID(id);

        return EntityDtoGiftCertificateMapper.toDto(giftCertificateList);
    }


    private List<Tag> createTagsIfNotFoundAndReturnAll(List<String> tagNamesList) {
        if (tagNamesList == null) {
            return new ArrayList<>();
        }
        List<Tag> tagList = new ArrayList<>();

        tagNamesList.forEach(tagName -> {
            Optional<Tag> optionalTag = tagDao.getTagByName(tagName);

            Tag tag = optionalTag.orElseGet(() -> tagDao.createTag(tagName));
            tagList.add(tag);
        });

        return tagList;
    }

}
