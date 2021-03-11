package com.epam.esm.service;

import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.model.dto.TagDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface provides methods to interact with TagRepository.
 * Methods should transforms received information into Repository-accepted data and invoke corresponding methods.
 */
public interface TagService {

    /**
     * Invokes Repository method to create Tag with provided data.
     *
     * @param tagDto is {@link TagDto} object with Tag data.
     * @return {@link TagDto} object with created data.
     */
    TagDto createTag(TagDto tagDto);

    /**
     * Invokes Repository method to delete Tag with provided id.
     *
     * @param id is id of tag to be deleted.
     */
    void deleteTag(int id);

    /**
     * Invokes Repository method to get Tag with provided id.
     *
     * @param id is id of tag to be returned.
     * @return {@link TagDto} object with tag data.
     */
    TagDto getTagByID(int id);

    /**
     * Invokes Repository method to get Tag with provided name.
     *
     * @param name is name of tag to be returned.
     * @return {@link TagDto} object with tag data.
     */
    TagDto getTagByName(String name);

    /**
     * Invokes Repository method to get List of all Tags from database.
     *
     * @param pageable is {@link Pageable} object with page number and page size
     * @return List of {@link TagDto} objects with tag data.
     */
    List<TagDto> getTags(Pageable pageable);


    /**
     * Invokes Repository method to get List of all Tags that linked with GiftCertificate by it's id
     *
     * @param id is id of GiftCertificate.
     * @return List of {@link TagDto} objects with tag data.
     */
    List<TagDto> getTagListByGiftCertificateID(int id);

    /**
     * Invokes Repository method to get the most widely used tag of a user with the highest cost of all orders
     *
     * @return {@link Tag} object with tag data.
     */
    TagDto getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders();

}
