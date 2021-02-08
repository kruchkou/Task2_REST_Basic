package com.epam.esm.service;

import com.epam.esm.service.model.dto.TagDTO;

import java.util.List;

/**
 * Interface provides methods to interact with TagDAO.
 * Methods should transforms received information into DAO-accepted data and invoke corresponding methods.
 */
public interface TagService {

    /**
     * Invokes DAO method to create Tag with provided data.
     *
     * @param tagDTO is {@link TagDTO} object with Tag data.
     * @return {@link TagDTO} object with created data.
     */
    TagDTO createTag(TagDTO tagDTO);

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
     * @return {@link TagDTO} object with tag data.
     */
    TagDTO getTagByID(int id);

    /**
     * Invokes DAO method to get Tag with provided name.
     *
     * @param name is name of tag to be returned.
     * @return {@link TagDTO} object with tag data.
     */
    TagDTO getTagByName(String name);

    /**
     * Invokes DAO method to get List of all Tags from database.
     *
     * @return List of {@link TagDTO} objects with tag data.
     */
    List<TagDTO> getTags();

    /**
     * Invokes DAO method to get List of all Tags that linked with GiftCertificate by it's id
     *
     * @param id is id of GiftCertificate.
     * @return List of {@link TagDTO} objects with tag data.
     */
    List<TagDTO> getTagListByGiftCertificateID(int id);

}
