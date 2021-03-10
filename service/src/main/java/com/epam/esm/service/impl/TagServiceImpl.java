package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.impl.DataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.exception.impl.TagAlreadyExistsException;
import com.epam.esm.service.exception.impl.TagNotFoundException;
import com.epam.esm.service.model.dto.TagDto;
import com.epam.esm.service.util.mapper.EntityDtoTagMapper;
import com.epam.esm.service.util.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link TagService}. Interface provides methods to interact with TagDao.
 * Methods should transforms received information into Dao-accepted data and invoke corresponding methods.
 */
@Service
public class TagServiceImpl implements TagService {

    /**
     * Format string to provide info by what id Tag wasn't found.
     */
    private static final String NOT_FOUND_BY_ID_PARAMETER = "id: %d";

    /**
     * Format string to provide info by what name Tag wasn't found.
     */
    private static final String NOT_FOUND_BY_NAME_PARAMETER = "name: %s";

    /**
     * Error message when Tag wasn't found by id
     */
    private static final String NO_TAG_WITH_ID_FOUND = "No tag with id: %d found";

    /**
     * Error message when Tag wasn't found by name
     */
    private static final String NO_TAG_WITH_NAME_FOUND = "No tag with name: %s found";

    /**
     * Error message when Tag with provided name already exists
     */
    private static final String TAG_ALREADY_EXISTS_EXCEPTION = "Tag with provided name already exists";

    /**
     * Error message when data failed validation
     */
    private static final String DATA_VALIDATION_EXCEPTION = "Data didn't passed validation";

    /**
     * Error code when data failed validation
     */
    private static final String ERROR_CODE_TAG_VALIDATION_FAILED = "0201";

    /**
     * Error code when Tag wasn't found by id
     */
    private static final String ERROR_CODE_TAG_BY_ID_NOT_FOUND_FAILED = "0202404%d";

    /**
     * Error code when Tag wasn't found by name
     */
    private static final String ERROR_CODE_TAG_BY_NAME_NOT_FOUND_FAILED = "0212404";

    /**
     * Error code when Tag with provided name already exists
     */
    private static final String ERROR_CODE_TAG_ALREADY_EXISTS = "0222";

    /**
     * Error message when GiftCertificate wasn't found by id
     */
    private static final String NO_GIFT_CERTIFICATE_WITH_ID_FOUND = "No certificate with id: %d found";

    /**
     * Error code when GiftCertificate wasn't found by id
     */
    private static final String ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED = "0102404%d";
    
    /**
     * An object of {@link TagDao}
     */
    private final TagDao tagDao;

    /**
     * An object of {@link TagDao}
     */
    private final GiftCertificateDao giftCertificateDao;

    /**
     * An object of {@link UserDao}
     */
    private final UserDao userDao;

    /**
     * Public constructor that receives tagDao
     *  @param tagDao is {@link TagDao} interface providing Dao methods.
     * @param giftCertificateDao is {@link GiftCertificateDao} interface providing Dao methods.
     * @param userDao is {@link UserDao} interface providing Dao methods.
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao, GiftCertificateDao giftCertificateDao, UserDao userDao) {
        this.tagDao = tagDao;
        this.giftCertificateDao = giftCertificateDao;
        this.userDao = userDao;
    }

    /**
     * Invokes Dao method to create Tag with provided data.
     *
     * @param tagDto is {@link TagDto} object with Tag data.
     * @return {@link TagDto} object with created data.
     * @throws DataValidationException if data failed validation
     */
    @Override
    @Transactional
    public TagDto createTag(TagDto tagDto) {
        if (!TagValidator.validateForCreate(tagDto)) {
            throw new DataValidationException(DATA_VALIDATION_EXCEPTION, ERROR_CODE_TAG_VALIDATION_FAILED);
        }

        String tagName = tagDto.getName();

        Optional<Tag> tagByName = tagDao.getTagByName(tagName);

        if(tagByName.isPresent()) {
            throw new TagAlreadyExistsException(TAG_ALREADY_EXISTS_EXCEPTION, ERROR_CODE_TAG_ALREADY_EXISTS);
        }

        Tag tag = tagDao.createTag(tagName);

        return EntityDtoTagMapper.toDto(tag);
    }

    /**
     * Invokes Dao method to delete Tag with provided id.
     *
     * @param id is id of tag to be deleted.
     * @throws TagNotFoundException if no Tag with provided id founded
     */
    @Override
    @Transactional
    public void deleteTag(int id) {
        if (!tagDao.getTagByID(id).isPresent()) {
            throw new TagNotFoundException(
                    String.format(NO_TAG_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_TAG_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }

        tagDao.deleteTag(id);
    }

    /**
     * Invokes Dao method to get Tag with provided id.
     *
     * @param id is id of tag to be returned.
     * @return {@link TagDto} object with tag data.
     * @throws TagNotFoundException if no Tag with provided id founded
     */
    @Override
    public TagDto getTagByID(int id) {
        Optional<Tag> optionalTag = tagDao.getTagByID(id);

        Tag tag = optionalTag.orElseThrow(() -> new TagNotFoundException(
                String.format(NO_TAG_WITH_ID_FOUND, id),
                String.format(ERROR_CODE_TAG_BY_ID_NOT_FOUND_FAILED, id),
                String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDtoTagMapper.toDto(tag);
    }

    /**
     * Invokes Dao method to get Tag with provided name.
     *
     * @param name is name of tag to be returned.
     * @return {@link TagDto} object with tag data.
     * @throws TagNotFoundException if no Tag with provided name founded
     */
    @Override
    public TagDto getTagByName(String name) {
        Optional<Tag> optionalTag = tagDao.getTagByName(name);

        Tag tag = optionalTag.orElseThrow(() -> new TagNotFoundException(
                String.format(NO_TAG_WITH_NAME_FOUND, name),
                ERROR_CODE_TAG_BY_NAME_NOT_FOUND_FAILED,
                String.format(NOT_FOUND_BY_NAME_PARAMETER, name)));

        return EntityDtoTagMapper.toDto(tag);
    }

    /**
     * Invokes Dao method to get List of all Tags from database.
     *
     * @param page is {@link Page} object with page number and page size
     * @return List of {@link TagDto} objects with tag data.
     */
    @Override
    public List<TagDto> getTags(Page page) {
        List<Tag> tagList = tagDao.getTags(page.getPage(), page.getSize());

        return EntityDtoTagMapper.toDto(tagList);
    }

    /**
     * Invokes Dao method to get List of all Tags that linked with GiftCertificate by it's id
     *
     * @param id is id of GiftCertificate.
     * @return List of {@link TagDto} objects with tag data.
     * @throws GiftCertificateByParameterNotFoundException if no Certificate with id found
     */
    @Override
    public List<TagDto> getTagListByGiftCertificateID(int id) {
        if (!giftCertificateDao.getGiftCertificateByID(id).isPresent()) {
            throw new GiftCertificateByParameterNotFoundException(
                    String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_GIFT_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }
        List<Tag> tagList = tagDao.getTagListByGiftCertificateID(id);

        return EntityDtoTagMapper.toDto(tagList);
    }

    /**
     * Invokes Dao method to get the most widely used tag of a user with the highest cost of all orders
     *
     * @return {@link Tag} object with tag data.
     */
    @Override
    public TagDto getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders() {
        User user = userDao.getUserWithHighestCostOfAllOrders();

        Tag tag  = tagDao.getMostWidelyUsedTagFromUser(user.getId());

        return EntityDtoTagMapper.toDto(tag);
    }
}
