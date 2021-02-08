package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.model.dto.TagDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is Mapper that links Tag Entities with DTOs.
 */
public final class EntityDTOTagMapper {

    private EntityDTOTagMapper() {
    }

    /**
     * Transforms DTO to Entity
     *
     * @param tagDTO is {@link TagDTO} object with data to transform
     * @return transformed to {@link Tag} entity.
     */
    public static Tag toEntity(TagDTO tagDTO) {
        Tag tag = new Tag();

        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());

        return tag;
    }

    /**
     * Transforms Entity to DTO
     *
     * @param tag is {@link Tag} object with data to transform
     * @return transformed to {@link TagDTO} data.
     */
    public static TagDTO toDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();

        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());

        return tagDTO;
    }

    /**
     * Transforms List of Entities to List of DTOs
     *
     * @param tagList is List of {@link Tag} object with data to transform
     * @return transformed to List of {@link TagDTO} data.
     */
    public static List<TagDTO> toDTO(List<Tag> tagList) {
        List<TagDTO> tagDTOList = new ArrayList<>();

        tagList.forEach(tag -> tagDTOList.add(toDTO(tag)));

        return tagDTOList;
    }

    /**
     * Transforms List of DTOs to List of Entities
     *
     * @param tagDTOList is List of {@link TagDTO} object with data to transform
     * @return transformed to List of {@link Tag} data.
     */
    public static List<Tag> toEntity(List<TagDTO> tagDTOList) {
        List<Tag> tagList = new ArrayList<>();

        tagDTOList.forEach(tagDTO -> tagList.add(toEntity(tagDTO)));

        return tagList;
    }
}
