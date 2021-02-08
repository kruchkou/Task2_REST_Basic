package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.TagDAO;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.exception.impl.TagAlreadyExistsException;
import com.epam.esm.service.exception.impl.DataValidationException;
import com.epam.esm.service.exception.impl.TagNotFoundException;
import com.epam.esm.service.model.dto.TagDto;
import com.epam.esm.service.util.mapper.EntityDtoTagMapper;
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

    private static final int TEST_ID = 1;
    private static final String TEST_NAME = "Tag";

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;

    private Tag tag;
    private TagDto testTagDTO;
    private TagDto emptyTagDTO;

    private List<Tag> tagList;
    private List<TagDto> testDTOList;
    @BeforeEach
    public void setUp() {
        tag = new Tag();
        tag.setId(TEST_ID);
        tag.setName(TEST_NAME);

        tagList = new ArrayList<>();
        tagList.add(tag);

        testDTOList = EntityDtoTagMapper.toDTO(tagList);

        emptyTagDTO = new TagDto();

        testTagDTO = new TagDto();
        testTagDTO.setName(TEST_NAME);
        testTagDTO.setId(TEST_ID);


        tagService = new TagServiceImpl(tagDAO, giftCertificateDAO);
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
    public void deleteTagShouldException() {
        given(tagDAO.getTagByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.deleteTag(TEST_ID));
    }

    @Test
    public void getTagByID() {
        given(tagDAO.getTagByID(TEST_ID)).willReturn(Optional.of(tag));
        TagDto receivedTagDTO = tagService.getTagByID(TEST_ID);

        assertEquals(testTagDTO, receivedTagDTO);
    }


    @Test
    public void getTagByIDShouldException() {
        given(tagDAO.getTagByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.getTagByID(TEST_ID));
    }

    @Test
    public void createTag() {
        given(tagDAO.createTag(any())).willReturn(tag);

        TagDto receivedDTO = tagService.createTag(testTagDTO);

        assertEquals(TEST_NAME, receivedDTO.getName());
    }

    @Test
    public void createTagShouldValidationException() {
        assertThrows(DataValidationException.class,
                () -> tagService.createTag(emptyTagDTO));
    }

    @Test
    public void createTagShouldAlreadyExistsException() {
        given(tagDAO.getTagByName(TEST_NAME)).willReturn(Optional.of(tag));

        assertThrows(TagAlreadyExistsException.class,
                () -> tagService.createTag(testTagDTO));
    }

    @Test
    public void getTags() {
        given(tagDAO.getTags()).willReturn(tagList);

        List<TagDto> receivedDTOList = tagService.getTags();

        assertIterableEquals(testDTOList, receivedDTOList);
    }


    @Test
    void getTagByName() {
        given(tagDAO.getTagByName(any())).willReturn(Optional.of(tag));

        TagDto receivedTagDTO = tagService.getTagByName(TEST_NAME);
        assertEquals(testTagDTO,receivedTagDTO);
    }

    @Test
    void getTagsByGiftCertificateID() {
        given(tagDAO.getTagListByGiftCertificateID(TEST_ID)).willReturn(tagList);
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(new GiftCertificate()));

        List<TagDto> receivedTagList = tagService.getTagListByGiftCertificateID(TEST_ID);
        assertEquals(testDTOList,receivedTagList);
    }

    @Test
    void getTagsByGiftCertificateShouldGiftNotFoundException() {
        assertThrows(GiftCertificateByParameterNotFoundException.class, () ->
                tagService.getTagListByGiftCertificateID(TEST_ID));
    }

}