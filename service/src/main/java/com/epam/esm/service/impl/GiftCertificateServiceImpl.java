package com.epam.esm.service.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.util.specification.GiftCertificateSpecification;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.impl.DataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.exception.impl.OrderNotFoundException;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.util.GiftCertificateFieldSetter;
import com.epam.esm.service.util.mapper.EntityDtoGiftCertificateMapper;
import com.epam.esm.service.util.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link GiftCertificateService}. Interface provides methods to interact with
 * GiftCertificateRepository.
 * Methods should transforms received information into Repository-accepted data and invoke corresponding methods.
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
     * An object of {@link GiftCertificateRepository}
     */
    private final GiftCertificateRepository giftCertificateRepository;

    /**
     * An object of {@link TagRepository}
     */
    private final TagRepository tagRepository;
    private final OrderRepository orderRepository;

    /**
     * Public constructor that receives giftCertificateRepository and tagRepository
     *
     * @param giftCertificateRepository is {@link GiftCertificateRepository} interface providing Repository methods.
     * @param tagRepository             is {@link TagRepository} interface providing Repository methods.
     * @param orderRepository           is {@link OrderRepository} interface providing Repository methods.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository, OrderRepository orderRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Invokes Repository method to delete GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be deleted.
     * @throws GiftCertificateByParameterNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    @Transactional
    public void deleteCertificate(int id) {
        if (!giftCertificateRepository.findById(id).isPresent()) {
            throw new GiftCertificateByParameterNotFoundException(
                    String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }

        giftCertificateRepository.deleteById(id);
    }

    /**
     * Invokes Repository method to get GiftCertificate with provided id.
     *
     * @param id is id of GiftCertificate to be returned.
     * @return {@link GiftCertificateDto} object with GiftCertificate data.
     * @throws GiftCertificateByParameterNotFoundException if no GiftCertificate with provided id founded
     */
    @Override
    public GiftCertificateDto getGiftCertificateByID(int id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);

        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(
                () -> new GiftCertificateByParameterNotFoundException(
                        String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                        String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                        String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDtoGiftCertificateMapper.toDto(giftCertificate);
    }

    /**
     * Invokes Repository method to update GiftCertificate with provided data.
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

        GiftCertificate giftCertificateFromDB = giftCertificateRepository.findById(id).orElseThrow(() ->
                new GiftCertificateByParameterNotFoundException(
                        String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                        String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                        String.format(NOT_FOUND_BY_ID_PARAMETER, id)));


        giftCertificateDto.setId(id);

        GiftCertificate giftCertificateFromDto = EntityDtoGiftCertificateMapper.toEntity(giftCertificateDto);

        List<Tag> tagList = createTagsIfNotFoundAndReturnAll(giftCertificateDto.getTags());
        giftCertificateFromDto.setTagList(tagList);

        LocalDateTime now = LocalDateTime.now();
        giftCertificateFromDto.setLastUpdateDate(now);

        GiftCertificate giftCertificate = GiftCertificateFieldSetter.update(giftCertificateFromDto,
                giftCertificateFromDB);

        GiftCertificate updatedGiftCertificate = giftCertificateRepository.save(giftCertificate);

        return EntityDtoGiftCertificateMapper.toDto(updatedGiftCertificate);
    }

    /**
     * Invokes Repository method to create GiftCertificate with provided data.
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

        GiftCertificate newGiftCertificate = giftCertificateRepository.save(giftCertificate);

        return EntityDtoGiftCertificateMapper.toDto(newGiftCertificate);
    }

    /**
     * Invokes Repository method to get List of all GiftCertificates from database.
     *
     * @param pageable is {@link Pageable} object with page number and page size
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    @Override
    public List<GiftCertificateDto> getCertificates(Pageable pageable) {
        List<GiftCertificate> giftCertificateList = giftCertificateRepository.findAll(pageable).toList();

        return EntityDtoGiftCertificateMapper.toDto(giftCertificateList);
    }

    /**
     * Invokes Repository method to get List of all GiftCertificates that matches parameters
     *
     * @param giftCertificateQueryParameter is {@link GetGiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    @Override
    public List<GiftCertificateDto> getCertificates(GetGiftCertificateQueryParameter giftCertificateQueryParameter,
                                                    Pageable pageable) {

        if (giftCertificateQueryParameter.isEmpty()) {
            return getCertificates(pageable);
        }

        List<GiftCertificate> giftCertificateList =
                giftCertificateRepository.findAll(GiftCertificateSpecification.findByQueryParameter(
                        giftCertificateQueryParameter), pageable).toList();

        return EntityDtoGiftCertificateMapper.toDto(giftCertificateList);
    }

    /**
     * Invokes Repository method to get List of all GiftCertificates that linked with Order by it's id
     *
     * @param id is id of Order.
     * @return List of {@link GiftCertificateDto} objects with giftCertificate data.
     * @throws OrderNotFoundException if no Certificate with id found
     */
    @Override
    public List<GiftCertificateDto> getCertificateListByOrderID(int id) {
        if (!orderRepository.findById(id).isPresent()) {
            throw new OrderNotFoundException(
                    String.format(NO_ORDER_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_ORDER_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }
        List<GiftCertificate> giftCertificateList =
                giftCertificateRepository.findAll(GiftCertificateSpecification.giftCertificateListByOrderId(id));

        return EntityDtoGiftCertificateMapper.toDto(giftCertificateList);
    }


    private List<Tag> createTagsIfNotFoundAndReturnAll(List<String> tagNamesList) {
        if (tagNamesList == null) {
            return new ArrayList<>();
        }
        List<Tag> tagList = new ArrayList<>();

        tagNamesList.forEach(tagName -> {
            Optional<Tag> optionalTag = tagRepository.findByName(tagName);

            Tag tagForSave = new Tag();
            tagForSave.setName(tagName);

            Tag tag = optionalTag.orElseGet(() -> tagRepository.save(tagForSave));
            tagList.add(tag);
        });

        return tagList;
    }

}
