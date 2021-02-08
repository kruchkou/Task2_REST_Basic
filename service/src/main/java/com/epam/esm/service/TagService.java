package com.epam.esm.service;

import com.epam.esm.service.model.dto.TagDto;

import java.util.List;

/**
 * Interface provides methods to interact with TagDAO.
 * Methods should transforms received information into DAO-accepted data and invoke corresponding methods.
 */
public interface TagService {

    /**
     * Invokes DAO method to create Tag with provided data.
     *
     * @param tagDTO is {@link TagDto} object with Tag data.
     * @return {@link TagDto} object with created data.
     */
    TagDto createTag(TagDto tagDTO);

    /**
     * Invokes DAO method to delete Tag with provided id.
     *
     * @param id is id of tag to be deleted.
     */
    void deleteTag(int id);

    /**
     * Invokes DAO method to get Tag with provided id.
     *
     * @param id is id of tag to be returned.
     * @return {@link TagDto} object with tag data.
     */
    TagDto getTagByID(int id);

    /**
     * Invokes DAO method to get Tag with provided name.
     *
     * @param name is name of tag to be returned.
     * @return {@link TagDto} object with tag data.
     */
    TagDto getTagByName(String name);

    /**
     * Invokes DAO method to get List of all Tags from database.
     *
     * @return List of {@link TagDto} objects with tag data.
     */
    List<TagDto> getTags();

    /**
     * Invokes DAO method to get List of all Tags that linked with GiftCertificate by it's id
     *
     * @param id is id of GiftCertificate.
     * @return List of {@link TagDto} objects with tag data.
     */
    List<TagDto> getTagListByGiftCertificateID(int id);

}
