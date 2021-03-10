package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.model.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDtoTagMapperTest {

    private static final Integer TEST_ID = 2;
    private static final String TEST_NAME = "Test Tag name";
    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    public void init() {
        tag = new Tag();
        tagDto = new TagDto();

        tag.setId(TEST_ID);
        tag.setName(TEST_NAME);

        tagDto.setId(TEST_ID);
        tagDto.setName(TEST_NAME);
    }

    @Test
    public void shouldConvertToEntity() {
        final Tag testTag = EntityDtoTagMapper.toEntity(tagDto);

        assertEquals(TEST_ID, testTag.getId());
        assertEquals(TEST_NAME, testTag.getName());
    }

    @Test
    public void shouldConvertToDto() {
        final TagDto testTag = EntityDtoTagMapper.toDto(tag);

        assertEquals(TEST_ID, testTag.getId());
        assertEquals(TEST_NAME, testTag.getName());
    }
}