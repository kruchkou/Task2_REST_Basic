package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.model.dto.TagDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is Mapper that links Tag Entities with Dtos.
 */
public final class EntityDtoTagMapper {

    private EntityDtoTagMapper() {
    }

    /**
     * Transforms Dto to Entity
     *
     * @param tagDto is {@link TagDto} object with data to transform
     * @return transformed to {@link Tag} entity.
     */
    public static Tag toEntity(TagDto tagDto) {
        Tag tag = new Tag();

        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName());

        return tag;
    }

    /**
     * Transforms Entity to Dto
     *
     * @param tag is {@link Tag} object with data to transform
     * @return transformed to {@link TagDto} data.
     */
    public static TagDto toDto(Tag tag) {
        TagDto tagDto = new TagDto();

        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());

        return tagDto;
    }

    /**
     * Transforms List of Entities to List of Dtos
     *
     * @param tagList is List of {@link Tag} object with data to transform
     * @return transformed to List of {@link TagDto} data.
     */
    public static List<TagDto> toDto(List<Tag> tagList) {
        List<TagDto> tagDtoList = new ArrayList<>();

        tagList.forEach(tag -> tagDtoList.add(toDto(tag)));

        return tagDtoList;
    }

    /**
     * Transforms List of Dtos to List of Entities
     *
     * @param tagDtoList is List of {@link TagDto} object with data to transform
     * @return transformed to List of {@link Tag} data.
     */
    public static List<Tag> toEntity(List<TagDto> tagDtoList) {
        List<Tag> tagList = new ArrayList<>();

        tagDtoList.forEach(tagDto -> tagList.add(toEntity(tagDto)));

        return tagList;
    }
}
