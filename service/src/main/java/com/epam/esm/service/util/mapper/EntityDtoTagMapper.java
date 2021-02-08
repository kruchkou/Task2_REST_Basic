package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.model.dto.TagDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is Mapper that links Tag Entities with DTOs.
 */
public final class EntityDtoTagMapper {

    private EntityDtoTagMapper() {
    }

    /**
     * Transforms DTO to Entity
     *
     * @param tagDTO is {@link TagDto} object with data to transform
     * @return transformed to {@link Tag} entity.
     */
    public static Tag toEntity(TagDto tagDTO) {
        Tag tag = new Tag();

        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());

        return tag;
    }

    /**
     * Transforms Entity to DTO
     *
     * @param tag is {@link Tag} object with data to transform
     * @return transformed to {@link TagDto} data.
     */
    public static TagDto toDTO(Tag tag) {
        TagDto tagDTO = new TagDto();

        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());

        return tagDTO;
    }

    /**
     * Transforms List of Entities to List of DTOs
     *
     * @param tagList is List of {@link Tag} object with data to transform
     * @return transformed to List of {@link TagDto} data.
     */
    public static List<TagDto> toDTO(List<Tag> tagList) {
        List<TagDto> tagDTOList = new ArrayList<>();

        tagList.forEach(tag -> tagDTOList.add(toDTO(tag)));

        return tagDTOList;
    }

    /**
     * Transforms List of DTOs to List of Entities
     *
     * @param tagDTOList is List of {@link TagDto} object with data to transform
     * @return transformed to List of {@link Tag} data.
     */
    public static List<Tag> toEntity(List<TagDto> tagDTOList) {
        List<Tag> tagList = new ArrayList<>();

        tagDTOList.forEach(tagDTO -> tagList.add(toEntity(tagDTO)));

        return tagList;
    }
}
