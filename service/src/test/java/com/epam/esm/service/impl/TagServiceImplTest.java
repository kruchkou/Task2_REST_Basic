package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.exception.impl.DataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.exception.impl.TagAlreadyExistsException;
import com.epam.esm.service.exception.impl.TagNotFoundException;
import com.epam.esm.service.model.dto.TagDto;
import com.epam.esm.service.util.mapper.EntityDtoTagMapper;
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
import static org.mockito.ArgumentMatchers.anyInt;
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
    private TagDao tagDao;
    @Mock
    private GiftCertificateDao giftCertificateDao;
    @Mock
    private UserDao userDao;

    private Tag tag;
    private TagDto testTagDto;
    private TagDto emptyTagDto;
    private Page page;

    private List<Tag> tagList;
    private List<TagDto> testDtoList;
    @BeforeEach
    public void setUp() {
        page = new Page();

        tag = new Tag();
        tag.setId(TEST_ID);
        tag.setName(TEST_NAME);

        tagList = new ArrayList<>();
        tagList.add(tag);

        testDtoList = EntityDtoTagMapper.toDto(tagList);

        emptyTagDto = new TagDto();

        testTagDto = new TagDto();
        testTagDto.setName(TEST_NAME);
        testTagDto.setId(TEST_ID);


        tagService = new TagServiceImpl(tagDao, giftCertificateDao, userDao);
    }

    @Test
    public void deleteTag() {
        given(tagDao.getTagByID(TEST_ID)).willReturn(Optional.of(tag));

        tagService.deleteTag(TEST_ID);

        verify(tagDao, times(1)).deleteTag(TEST_ID);
    }

    @Test
    public void deleteTagShouldException() {
        given(tagDao.getTagByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.deleteTag(TEST_ID));
    }

    @Test
    public void getTagByID() {
        given(tagDao.getTagByID(TEST_ID)).willReturn(Optional.of(tag));
        TagDto receivedTagDto = tagService.getTagByID(TEST_ID);

        assertEquals(testTagDto, receivedTagDto);
    }


    @Test
    public void getTagByIDShouldException() {
        given(tagDao.getTagByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.getTagByID(TEST_ID));
    }

    @Test
    public void createTag() {
        given(tagDao.createTag(any())).willReturn(tag);

        TagDto receivedDto = tagService.createTag(testTagDto);

        assertEquals(TEST_NAME, receivedDto.getName());
    }

    @Test
    public void createTagShouldValidationException() {
        assertThrows(DataValidationException.class,
                () -> tagService.createTag(emptyTagDto));
    }

    @Test
    public void createTagShouldAlreadyExistsException() {
        given(tagDao.getTagByName(TEST_NAME)).willReturn(Optional.of(tag));

        assertThrows(TagAlreadyExistsException.class,
                () -> tagService.createTag(testTagDto));
    }

    @Test
    public void getTags() {
        given(tagDao.getTags(anyInt(),anyInt())).willReturn(tagList);

        List<TagDto> receivedDtoList = tagService.getTags(page);

        assertIterableEquals(testDtoList, receivedDtoList);
    }


    @Test
    public void getTagByName() {
        given(tagDao.getTagByName(any())).willReturn(Optional.of(tag));

        TagDto receivedTagDto = tagService.getTagByName(TEST_NAME);
        assertEquals(testTagDto,receivedTagDto);
    }

    @Test
    public void getTagsByGiftCertificateID() {
        given(tagDao.getTagListByGiftCertificateID(TEST_ID)).willReturn(tagList);
        given(giftCertificateDao.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(new GiftCertificate()));

        List<TagDto> receivedTagList = tagService.getTagListByGiftCertificateID(TEST_ID);
        assertEquals(testDtoList,receivedTagList);
    }

    @Test
    public void getTagsByGiftCertificateShouldGiftNotFoundException() {
        assertThrows(GiftCertificateByParameterNotFoundException.class, () ->
                tagService.getTagListByGiftCertificateID(TEST_ID));
    }

}