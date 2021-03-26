package com.epam.esm.service.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.exception.impl.DataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.util.mapper.EntityDtoGiftCertificateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private static final int TEST_ID = 1;
    private static final int TEST_TAG_ID = 2;
    private static final String TEST_TAG_NAME = "Test tag";
    private static final String TEST_NAME = "Cert";
    private static final String TEST_DESCRIPTION = "certificate";
    private static final String TEST_NEW_NAME = "New name";
    private static final String TEST_NEW_DESCRIPTION = "New description";
    private static final int TEST_PRICE = 10;
    private static final int TEST_DURATION = 30;
    private static final int CORRECT_SIZE = 3;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private OrderRepository orderRepository;

    private Tag testTag;
    private GiftCertificate giftCertificate;
    private GiftCertificateDto notFullyValuedGiftCertificateDto;
    private GiftCertificateDto dtoWithoutID;
    private GiftCertificateDto testGiftCertificateDto;
    private GetGiftCertificateQueryParameter emptyQueryParameter;
    private GetGiftCertificateQueryParameter queryParameter;

    private List<String> tagNamesList;
    private List<Tag> giftTagList;
    private List<GiftCertificate> giftCertificateList;
    private List<GiftCertificateDto> testGiftCertificateDtoList;

    @BeforeEach
    public void setUp() {

        testTag = new Tag();
        testTag.setId(TEST_TAG_ID);
        testTag.setName(TEST_TAG_NAME);

        giftTagList = new ArrayList<>();
        giftTagList.add(testTag);

        giftCertificate = new GiftCertificate();
        giftCertificate.setId(TEST_ID);
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
        giftCertificate.setTagList(giftTagList);

        notFullyValuedGiftCertificateDto = new GiftCertificateDto();
        notFullyValuedGiftCertificateDto.setName(TEST_NEW_NAME);
        notFullyValuedGiftCertificateDto.setDescription(TEST_NEW_DESCRIPTION);

        tagNamesList = new ArrayList<>();
        tagNamesList.add(TEST_TAG_NAME);

        testGiftCertificateDto = new GiftCertificateDto();
        testGiftCertificateDto.setId(TEST_ID);
        testGiftCertificateDto.setName(TEST_NAME);
        testGiftCertificateDto.setDescription(TEST_DESCRIPTION);
        testGiftCertificateDto.setPrice(TEST_PRICE);
        testGiftCertificateDto.setDuration(TEST_DURATION);
        testGiftCertificateDto.setTags(tagNamesList);

        dtoWithoutID = new GiftCertificateDto();
        dtoWithoutID.setName(TEST_NAME);
        dtoWithoutID.setDescription(TEST_DESCRIPTION);
        dtoWithoutID.setPrice(TEST_PRICE);
        dtoWithoutID.setDuration(TEST_DURATION);
        dtoWithoutID.setTags(tagNamesList);

        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(giftCertificate);
        giftCertificateList.add(giftCertificate);
        giftCertificateList.add(giftCertificate);

        testGiftCertificateDtoList = EntityDtoGiftCertificateMapper.toDto(giftCertificateList);

        emptyQueryParameter = new GetGiftCertificateQueryParameter();
        queryParameter = new GetGiftCertificateQueryParameter();

        queryParameter.setName(TEST_NEW_NAME);
        queryParameter.setTagName(tagNamesList);

        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository, tagRepository, orderRepository);
    }

    @Test
    public void deleteCertificate() {
        given(giftCertificateRepository.findById(TEST_ID)).willReturn(Optional.of(giftCertificate));

        giftCertificateService.deleteCertificate(TEST_ID);

        verify(giftCertificateRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    public void deleteCertificateShouldException() {
        given(giftCertificateRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class, () -> giftCertificateService.deleteCertificate(TEST_ID));
    }

    @Test
    public void getGiftCertificateByID() {
        given(giftCertificateRepository.findById(TEST_ID)).willReturn(Optional.of(giftCertificate));
        GiftCertificateDto receivedCertificateDto = giftCertificateService.getGiftCertificateByID(TEST_ID);


        assertEquals(testGiftCertificateDto, receivedCertificateDto);
    }


    @Test
    public void getGiftCertificateByIDShouldException() {
        given(giftCertificateRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class, () -> giftCertificateService.getGiftCertificateByID(TEST_ID));
    }


    @Test
    public void updateCertificate() {
        given(giftCertificateRepository.findById(TEST_ID)).willReturn(Optional.of(giftCertificate));
        given(giftCertificateRepository.save(any())).willReturn(giftCertificate);
        given(tagRepository.findByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));

        GiftCertificateDto receivedDto = giftCertificateService.updateCertificate(dtoWithoutID, TEST_ID);
        testGiftCertificateDto.setLastUpdateDate(receivedDto.getLastUpdateDate());
        assertEquals(testGiftCertificateDto, receivedDto);
    }

    @Test
    public void updateCertificateShouldException() {
        given(giftCertificateRepository.findById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class,
                () -> giftCertificateService.updateCertificate(dtoWithoutID, TEST_ID));
    }

    @Test
    public void createGiftCertificate() {
        given(giftCertificateRepository.save(any())).willReturn(giftCertificate);
        given(tagRepository.findByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));

        GiftCertificateDto receivedDto = giftCertificateService.createGiftCertificate(dtoWithoutID);

        assertEquals(TEST_NAME, receivedDto.getName());
        assertEquals(TEST_DESCRIPTION, receivedDto.getDescription());
        assertEquals(TEST_PRICE, receivedDto.getPrice());
        assertEquals(TEST_DURATION, receivedDto.getDuration());
        assertEquals(tagNamesList, receivedDto.getTags());
    }

    @Test
    public void createGiftCertificateShouldException() {
        assertThrows(DataValidationException.class,
                () -> giftCertificateService.createGiftCertificate(notFullyValuedGiftCertificateDto));
    }

    @Test
    public void getCertificates() {
        org.springframework.data.domain.Page<GiftCertificate> pageable = mock(org.springframework.data.domain.Page.class);
        when(pageable.toList()).thenReturn(giftCertificateList);

        given(giftCertificateRepository.findAll(any(Pageable.class))).willReturn(pageable);

        List<GiftCertificateDto> receivedDtoList = giftCertificateService.getCertificates(Pageable.unpaged());

        assertIterableEquals(testGiftCertificateDtoList, receivedDtoList);
    }

    @Test
    public void getCertificatesWithEmptyQueryParameter() {
        org.springframework.data.domain.Page<GiftCertificate> pageable = mock(org.springframework.data.domain.Page.class);
        when(pageable.toList()).thenReturn(giftCertificateList);

        given(giftCertificateRepository.findAll(any(Pageable.class)))
                .willReturn(pageable);

        giftCertificateService.getCertificates(emptyQueryParameter, Pageable.unpaged());

        verify(giftCertificateRepository).findAll(any(Pageable.class));
    }

    @Test
    public void getCertificatesByQueryParameter() {
        org.springframework.data.domain.Page<GiftCertificate> pageable = mock(org.springframework.data.domain.Page.class);
        when(pageable.toList()).thenReturn(giftCertificateList);

        given(giftCertificateRepository.findAll(Mockito.any(Specification.class), any(Pageable.class)))
                .willReturn(pageable);

        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateService.getCertificates(queryParameter,
                Pageable.unpaged());

        assertEquals(CORRECT_SIZE, giftCertificateDtoList.size());
    }
}