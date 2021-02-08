package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.TagDAO;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.exception.impl.GiftCertificateDataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.util.mapper.EntityDtoGiftCertificateMapper;
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
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagDAO tagDAO;

    private Tag testTag;
    private GiftCertificate giftCertificate;
    private GiftCertificateDto notFullyValuedGiftCertificateDTO;
    private GiftCertificateDto dtoWithoutID;
    private GiftCertificateDto testGiftCertificateDTO;
    private GetGiftCertificateQueryParameter emptyQueryParameter;
    private GetGiftCertificateQueryParameter queryParameter;

    private List<String> tagNamesList;
    private List<Tag> giftTagList;
    private List<GiftCertificate> giftCertificateList;
    private List<GiftCertificateDto> testGiftCertificateDTOList;

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

        notFullyValuedGiftCertificateDTO = new GiftCertificateDto();
        notFullyValuedGiftCertificateDTO.setName(TEST_NEW_NAME);
        notFullyValuedGiftCertificateDTO.setDescription(TEST_NEW_DESCRIPTION);

        tagNamesList = new ArrayList<>();
        tagNamesList.add(TEST_TAG_NAME);

        testGiftCertificateDTO = new GiftCertificateDto();
        testGiftCertificateDTO.setId(TEST_ID);
        testGiftCertificateDTO.setName(TEST_NAME);
        testGiftCertificateDTO.setDescription(TEST_DESCRIPTION);
        testGiftCertificateDTO.setPrice(TEST_PRICE);
        testGiftCertificateDTO.setDuration(TEST_DURATION);
        testGiftCertificateDTO.setTags(tagNamesList);

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

        testGiftCertificateDTOList = EntityDtoGiftCertificateMapper.toDTO(giftCertificateList);

        emptyQueryParameter = new GetGiftCertificateQueryParameter();
        queryParameter = new GetGiftCertificateQueryParameter();

        queryParameter.setName(TEST_NEW_NAME);
        queryParameter.setTagName(tagNamesList);

        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDAO, tagDAO);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void deleteCertificate() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(giftCertificate));

        giftCertificateService.deleteCertificate(TEST_ID);

        verify(giftCertificateDAO, times(1)).deleteGiftCertificate(TEST_ID);
    }

    @Test
    public void deleteCertificateShouldException() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class, () -> giftCertificateService.deleteCertificate(TEST_ID));
    }

    @Test
    public void getGiftCertificateByID() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(giftCertificate));
        GiftCertificateDto receivedCertificateDTO = giftCertificateService.getGiftCertificateByID(TEST_ID);


        assertEquals(testGiftCertificateDTO, receivedCertificateDTO);
    }


    @Test
    public void getGiftCertificateByIDShouldException() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class, () -> giftCertificateService.getGiftCertificateByID(TEST_ID));
    }


    @Test
    public void updateCertificate() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(giftCertificate));
        given(giftCertificateDAO.updateGiftCertificate(any(), anyInt())).willReturn(giftCertificate);
        given(tagDAO.getTagByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));

        GiftCertificateDto receivedDTO = giftCertificateService.updateCertificate(dtoWithoutID, TEST_ID);
        assertEquals(testGiftCertificateDTO, receivedDTO);
    }

    @Test
    public void updateCertificateShouldException() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class,
                () -> giftCertificateService.updateCertificate(any(), TEST_ID));
    }

    @Test
    public void createGiftCertificate() {
        given(giftCertificateDAO.createGiftCertificate(any())).willReturn(giftCertificate);
        given(giftCertificateDAO.getGiftCertificateByID(anyInt())).willReturn(Optional.of(giftCertificate));
        given(tagDAO.getTagByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));

        GiftCertificateDto receivedDTO = giftCertificateService.createGiftCertificate(dtoWithoutID);

        assertEquals(TEST_NAME, receivedDTO.getName());
        assertEquals(TEST_DESCRIPTION, receivedDTO.getDescription());
        assertEquals(TEST_PRICE, receivedDTO.getPrice());
        assertEquals(TEST_DURATION, receivedDTO.getDuration());
        assertEquals(tagNamesList, receivedDTO.getTags());
    }

    @Test
    public void createGiftCertificateShouldException() {
        assertThrows(GiftCertificateDataValidationException.class,
                () -> giftCertificateService.createGiftCertificate(notFullyValuedGiftCertificateDTO));
    }

    @Test
    public void getCertificates() {
        given(giftCertificateDAO.getGiftCertificates()).willReturn(giftCertificateList);

        List<GiftCertificateDto> receivedDTOList = giftCertificateService.getCertificates();

        assertIterableEquals(testGiftCertificateDTOList, receivedDTOList);
    }

    @Test
    public void getCertificatesWithEmptyQueryParameter() {
        giftCertificateService.getCertificates(emptyQueryParameter);

        verify(giftCertificateDAO).getGiftCertificates();
    }

    @Test
    public void getCertificatesByQueryParameter() {
        final int CORRECT_SIZE = 3;

        given(giftCertificateDAO.getGiftCertificates(queryParameter)).willReturn(giftCertificateList);

        List<GiftCertificateDto> giftCertificateDTOList = giftCertificateService.getCertificates(queryParameter);

        verify(giftCertificateDAO).getGiftCertificates(queryParameter);
        assertEquals(CORRECT_SIZE, giftCertificateDTOList.size());
    }
}