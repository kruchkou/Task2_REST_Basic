package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.TagDAO;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.exception.impl.TagDataValidationException;
import com.epam.esm.service.exception.impl.TagNotFoundException;
import com.epam.esm.service.model.dto.TagDTO;
import com.epam.esm.service.util.mapper.EntityDTOTagMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private final static int TEST_ID = 1;
    private final static String TEST_NAME = "Tag";

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;

    private Tag tag;
    private TagDTO tagDTO;
    private TagDTO emptyTagDTO;

    private List<Tag> tagList;

    @BeforeEach
    public void setUp() {
        tag = new Tag();
        tag.setId(TEST_ID);
        tag.setName(TEST_NAME);

        tagList = new ArrayList<>();
        tagList.add(tag);

        tag = new Tag();
        tag.setId(TEST_ID);
        tag.setName(TEST_NAME);

        emptyTagDTO = new TagDTO();

        tagDTO = new TagDTO();
        tagDTO.setName(TEST_NAME);

        tagService = new TagServiceImpl(tagDAO);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void deleteTag() {
        given(tagDAO.getTagByID(TEST_ID)).willReturn(Optional.of(tag));

        tagService.deleteTag(TEST_ID);

        verify(tagDAO, times(1)).deleteTag(TEST_ID);
    }

    @Test
    public void deleteCertificateShouldException() {
        given(tagDAO.getTagByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.deleteTag(TEST_ID));
    }

    @Test
    public void getGiftCertificateByID() {
        given(tagDAO.getTagByID(TEST_ID)).willReturn(Optional.of(tag));
        TagDTO receivedTagDTO = tagService.getTagByID(TEST_ID);

        TagDTO testedDTO = EntityDTOTagMapper.toDTO(tag);
        assertEquals(testedDTO, receivedTagDTO);
    }


    @Test
    public void getGiftCertificateByIDShouldException() {
        given(tagDAO.getTagByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.getTagByID(TEST_ID));
    }

    @Test
    public void createGiftCertificate() {
        given(tagDAO.createTag(any())).willReturn(tag);

        TagDTO receivedDTO = tagService.createTag(tagDTO);

        assertEquals(TEST_NAME, receivedDTO.getName());
    }

    @Test
    public void createGiftCertificateShouldException() {
        assertThrows(TagDataValidationException.class,
                () -> tagService.createTag(emptyTagDTO));
    }

    @Test
    public void getCertificates() {
        given(tagDAO.getTags()).willReturn(tagList);

        List<TagDTO> receivedDTOList = tagService.getTags();
        List<TagDTO> testDTOList = EntityDTOTagMapper.toDTO(tagList);

        assertIterableEquals(testDTOList, receivedDTOList);
    }

}