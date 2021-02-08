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
    private TagDto tagDTO;

    @BeforeEach
    public void init() {
        tag = new Tag();
        tagDTO = new TagDto();

        tag.setId(TEST_ID);
        tag.setName(TEST_NAME);

        tagDTO.setId(TEST_ID);
        tagDTO.setName(TEST_NAME);
    }

    @Test
    public void shouldConvertToEntity() {
        final Tag testTag = EntityDtoTagMapper.toEntity(tagDTO);

        assertEquals(TEST_ID, testTag.getId());
        assertEquals(TEST_NAME, testTag.getName());
    }

    @Test
    public void shouldConvertToDTO() {
        final TagDto testTag = EntityDtoTagMapper.toDTO(tag);

        assertEquals(TEST_ID, testTag.getId());
        assertEquals(TEST_NAME, testTag.getName());
    }
}