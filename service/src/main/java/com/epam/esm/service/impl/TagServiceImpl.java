package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.TagDAO;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.impl.TagDataValidationException;
import com.epam.esm.service.exception.impl.TagNotFoundException;
import com.epam.esm.service.model.dto.TagDTO;
import com.epam.esm.service.util.mapper.EntityDTOTagMapper;
import com.epam.esm.service.util.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link TagService}. Interface provides methods to interact with TagDAO.
 * Methods should transforms received information into DAO-accepted data and invoke corresponding methods.
 */
@Service
public class TagServiceImpl implements TagService {

    /**
     * An object of {@link TagDAO}
     */
    private final TagDAO tagDAO;


    /**
     * Format string to provide info by what id Tag wasn't found.
     */
    private final static String NOT_FOUND_BY_ID_PARAMETER = "id: %d";

    /**
     * Format string to provide info by what name Tag wasn't found.
     */
    private final static String NOT_FOUND_BY_NAME_PARAMETER = "name: %s";

    /**
     * Error message when Tag wasn't found by id
     */
    private final static String NO_TAG_WITH_ID_FOUND = "No tag with id: %d found";

    /**
     * Error message when Tag wasn't found by name
     */
    private final static String NO_TAG_WITH_NAME_FOUND = "No tag with name: %s found";

    /**
     * Error message when data failed validation
     */
    private final static String DATA_VALIDATION_EXCEPTION = "Data didn't passed validation";

    /**
     * Error code when data failed validation
     */
    private final static String ERROR_CODE_TAG_VALIDATION_FAILED = "0201";

    /**
     * Error code when Tag wasn't found by id
     */
    private final static String ERROR_CODE_TAG_BY_ID_NOT_FOUND_FAILED = "0202404%d";

    /**
     * Error code when Tag wasn't found by name
     */
    private final static String ERROR_CODE_TAG_BY_NAME_NOT_FOUND_FAILED = "0212404";

    /**
     * Public constructor that receives tagDAO
     *
     * @param tagDAO is {@link TagDAO} interface providing DAO methods.
     */
    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    /**
     * Invokes DAO method to create Tag with provided data.
     *
     * @param tagDTO is {@link TagDTO} object with Tag data.
     * @return {@link TagDTO} object with created data.
     * @throws TagDataValidationException if data failed validation
     */
    @Override
    @Transactional
    public TagDTO createTag(TagDTO tagDTO) {
        if (!TagValidator.validateForCreate(tagDTO)) {
            throw new TagDataValidationException(DATA_VALIDATION_EXCEPTION, ERROR_CODE_TAG_VALIDATION_FAILED);
        }

        String tagName = tagDTO.getName();
        Tag tag = tagDAO.createTag(tagName);

        return EntityDTOTagMapper.toDTO(tag);
    }

    /**
     * Invokes DAO method to delete Tag with provided id.
     *
     * @param id is id of tag to be deleted.
     * @throws TagNotFoundException if no Tag with provided id founded
     */
    @Override
    @Transactional
    public void deleteTag(int id) {
        if (!tagDAO.getTagByID(id).isPresent()) {
            throw new TagNotFoundException(
                    String.format(NO_TAG_WITH_ID_FOUND, id),
                    String.format(ERROR_CODE_TAG_BY_ID_NOT_FOUND_FAILED, id),
                    String.format(NOT_FOUND_BY_ID_PARAMETER, id));
        }

        tagDAO.deleteTag(id);
    }

    /**
     * Invokes DAO method to get Tag with provided id.
     *
     * @param id is id of tag to be returned.
     * @return {@link TagDTO} object with tag data.
     * @throws TagNotFoundException if no Tag with provided id founded
     */
    @Override
    public TagDTO getTagByID(int id) {
        Optional<Tag> optionalTag = tagDAO.getTagByID(id);

        Tag tag = optionalTag.orElseThrow(() -> new TagNotFoundException(
                String.format(NO_TAG_WITH_ID_FOUND, id),
                String.format(ERROR_CODE_TAG_BY_ID_NOT_FOUND_FAILED, id),
                String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDTOTagMapper.toDTO(tag);
    }

    /**
     * Invokes DAO method to get Tag with provided name.
     *
     * @param name is name of tag to be returned.
     * @return {@link TagDTO} object with tag data.
     * @throws TagNotFoundException if no Tag with provided name founded
     */
    @Override
    public TagDTO getTagByName(String name) {
        Optional<Tag> optionalTag = tagDAO.getTagByName(name);

        Tag tag = optionalTag.orElseThrow(() -> new TagNotFoundException(
                String.format(NO_TAG_WITH_NAME_FOUND, name),
                ERROR_CODE_TAG_BY_NAME_NOT_FOUND_FAILED,
                String.format(NOT_FOUND_BY_NAME_PARAMETER, name)));

        return EntityDTOTagMapper.toDTO(tag);
    }

    /**
     * Invokes DAO method to get List of all Tags from database.
     *
     * @return List of {@link TagDTO} objects with tag data.
     */
    @Override
    public List<TagDTO> getTags() {
        List<Tag> tagList = tagDAO.getTags();

        return EntityDTOTagMapper.toDTO(tagList);
    }

    /**
     * Invokes DAO method to get List of all Tags that linked with GiftCertificate by it's id
     *
     * @param id is id of GiftCertificate.
     * @return List of {@link TagDTO} objects with tag data.
     */
    @Override
    public List<TagDTO> getTagListByGiftCertificateID(int id) {
        List<Tag> tagList = tagDAO.getTagListByGiftCertificateID(id);

        return EntityDTOTagMapper.toDTO(tagList);
    }
}
