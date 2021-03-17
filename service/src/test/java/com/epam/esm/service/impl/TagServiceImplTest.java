package com.epam.esm.service.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final int TEST_ID = 1;
    private static final String TEST_NAME = "Tag";

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private UserRepository userRepository;

    private Tag tag;
    private TagDto testTagDto;
    private TagDto testForCreationTagDto;
    private TagDto emptyTagDto;

    private List<Tag> tagList;
    private List<TagDto> testDtoList;

    @BeforeEach
    public void setUp() {

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

        testForCreationTagDto = new TagDto();
        testForCreationTagDto.setName(TEST_NAME);


        tagService = new TagServiceImpl(tagRepository, giftCertificateRepository, userRepository);
    }

    @Test
    public void deleteTag() {
        given(tagRepository.findById(TEST_ID)).willReturn(Optional.of(tag));

        tagService.deleteTag(TEST_ID);

        verify(tagRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    public void deleteTagShouldException() {
        given(tagRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.deleteTag(TEST_ID));
    }

    @Test
    public void getTagByID() {
        given(tagRepository.findById(TEST_ID)).willReturn(Optional.of(tag));
        TagDto receivedTagDto = tagService.getTagByID(TEST_ID);

        assertEquals(testTagDto, receivedTagDto);
    }


    @Test
    public void getTagByIDShouldException() {
        given(tagRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.getTagByID(TEST_ID));
    }

    @Test
    public void createTag() {
        given(tagRepository.save(any())).willReturn(tag);

        TagDto receivedDto = tagService.createTag(testForCreationTagDto);

        assertEquals(TEST_NAME, receivedDto.getName());
    }

    @Test
    public void createTagShouldValidationException() {
        assertThrows(DataValidationException.class,
                () -> tagService.createTag(emptyTagDto));
    }

    @Test
    public void createTagShouldAlreadyExistsException() {
        given(tagRepository.findByName(any())).willReturn(Optional.of(tag));

        assertThrows(TagAlreadyExistsException.class,
                () -> tagService.createTag(testForCreationTagDto));
    }

    @Test
    public void getTags() {
        org.springframework.data.domain.Page<Tag> pageable = mock(org.springframework.data.domain.Page.class);
        when(pageable.toList()).thenReturn(tagList);

        given(tagRepository.findAll(any(Pageable.class))).willReturn(pageable);

        List<TagDto> receivedDtoList = tagService.getTags(Pageable.unpaged());

        assertIterableEquals(testDtoList, receivedDtoList);
    }


    @Test
    public void getTagByName() {
        given(tagRepository.findByName(any())).willReturn(Optional.of(tag));

        TagDto receivedTagDto = tagService.getTagByName(TEST_NAME);
        assertEquals(testTagDto, receivedTagDto);
    }

    @Test
    public void getTagsByGiftCertificateID() {
        given(tagRepository.findAll(any(Specification.class))).willReturn(tagList);
        given(giftCertificateRepository.findById(anyInt())).willReturn(Optional.of(new GiftCertificate()));

        List<TagDto> receivedTagList = tagService.getTagListByGiftCertificateID(TEST_ID);
        assertEquals(testDtoList, receivedTagList);
    }

    @Test
    public void getTagsByGiftCertificateShouldGiftNotFoundException() {
        assertThrows(GiftCertificateByParameterNotFoundException.class, () ->
                tagService.getTagListByGiftCertificateID(TEST_ID));
    }

}