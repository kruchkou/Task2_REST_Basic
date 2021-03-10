package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.OrderDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.exception.impl.DataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.util.mapper.EntityDtoGiftCertificateMapper;
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

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDao giftCertificateDao;
    @Mock
    private TagDao tagDao;
    @Mock
    private OrderDao orderDao;

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
    private Page page;

    @BeforeEach
    public void setUp() {
        page = Page.def();

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

        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagDao, orderDao);
    }

    @Test
    public void deleteCertificate() {
        given(giftCertificateDao.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(giftCertificate));

        giftCertificateService.deleteCertificate(TEST_ID);

        verify(giftCertificateDao, times(1)).deleteGiftCertificate(TEST_ID);
    }

    @Test
    public void deleteCertificateShouldException() {
        given(giftCertificateDao.getGiftCertificateByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class, () -> giftCertificateService.deleteCertificate(TEST_ID));
    }

    @Test
    public void getGiftCertificateByID() {
        given(giftCertificateDao.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(giftCertificate));
        GiftCertificateDto receivedCertificateDto = giftCertificateService.getGiftCertificateByID(TEST_ID);


        assertEquals(testGiftCertificateDto, receivedCertificateDto);
    }


    @Test
    public void getGiftCertificateByIDShouldException() {
        given(giftCertificateDao.getGiftCertificateByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class, () -> giftCertificateService.getGiftCertificateByID(TEST_ID));
    }


    @Test
    public void updateCertificate() {
        given(giftCertificateDao.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(giftCertificate));
        given(giftCertificateDao.updateGiftCertificate(any(), anyInt())).willReturn(giftCertificate);
        given(tagDao.getTagByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));

        GiftCertificateDto receivedDto = giftCertificateService.updateCertificate(dtoWithoutID, TEST_ID);
        assertEquals(testGiftCertificateDto, receivedDto);
    }

    @Test
    public void updateCertificateShouldException() {
        given(giftCertificateDao.getGiftCertificateByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class,
                () -> giftCertificateService.updateCertificate(any(), TEST_ID));
    }

    @Test
    public void createGiftCertificate() {
        given(giftCertificateDao.createGiftCertificate(any())).willReturn(giftCertificate);
        given(giftCertificateDao.getGiftCertificateByID(anyInt())).willReturn(Optional.of(giftCertificate));
        given(tagDao.getTagByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));

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
        given(giftCertificateDao.getGiftCertificates(any())).willReturn(giftCertificateList);

        List<GiftCertificateDto> receivedDtoList = giftCertificateService.getCertificates(page);

        assertIterableEquals(testGiftCertificateDtoList, receivedDtoList);
    }

    @Test
    public void getCertificatesWithEmptyQueryParameter() {
        giftCertificateService.getCertificates(emptyQueryParameter);

        verify(giftCertificateDao).getGiftCertificates(any());
    }

    @Test
    public void getCertificatesByQueryParameter() {
        final int CORRECT_SIZE = 3;

        given(giftCertificateDao.getGiftCertificates(queryParameter)).willReturn(giftCertificateList);

        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateService.getCertificates(queryParameter);

        verify(giftCertificateDao).getGiftCertificates(queryParameter);
        assertEquals(CORRECT_SIZE, giftCertificateDtoList.size());
    }
}