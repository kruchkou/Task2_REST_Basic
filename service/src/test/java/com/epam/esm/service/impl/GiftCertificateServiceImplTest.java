package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.TagDAO;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.exception.impl.GiftCertificateDataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateNotFoundException;
import com.epam.esm.service.model.dto.GiftCertificateDTO;
import com.epam.esm.service.util.mapper.EntityDTOGiftCertificateMapper;
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

    private final static int TEST_ID = 1;
    private final static int TEST_TAG_ID = 2;
    private final static String TEST_TAG_NAME = "Test tag";
    private final static String TEST_NAME = "Cert";
    private final static String TEST_DESCRIPTION = "certificate";
    private final static String TEST_NEW_NAME = "New name";
    private final static String TEST_NEW_DESCRIPTION = "New description";
    private final static int TEST_PRICE = 10;
    private final static int TEST_DURATION = 30;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagDAO tagDAO;

    private Tag testTag;
    private GiftCertificate giftCertificate;
    private GiftCertificateDTO notFullyValuedGiftCertificateDTO;
    private GiftCertificateDTO validGiftCertificateDTO;
    private GetGiftCertificateQueryParameter emptyQueryParameter;
    private GetGiftCertificateQueryParameter queryParameter;

    private List<String> tagNamesList;
    private List<Tag> giftTagList;
    private List<GiftCertificate> giftCertificateList;

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

        notFullyValuedGiftCertificateDTO = new GiftCertificateDTO();
        notFullyValuedGiftCertificateDTO.setName(TEST_NEW_NAME);
        notFullyValuedGiftCertificateDTO.setDescription(TEST_NEW_DESCRIPTION);

        tagNamesList = new ArrayList<>();
        tagNamesList.add(TEST_TAG_NAME);

        validGiftCertificateDTO = new GiftCertificateDTO();
        validGiftCertificateDTO.setName(TEST_NAME);
        validGiftCertificateDTO.setDescription(TEST_DESCRIPTION);
        validGiftCertificateDTO.setPrice(TEST_PRICE);
        validGiftCertificateDTO.setDuration(TEST_DURATION);
        validGiftCertificateDTO.setTagNames(tagNamesList);

        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(giftCertificate);
        giftCertificateList.add(giftCertificate);
        giftCertificateList.add(giftCertificate);

        emptyQueryParameter = new GetGiftCertificateQueryParameter();
        queryParameter = new GetGiftCertificateQueryParameter();

        queryParameter.setName(TEST_NEW_NAME);
        queryParameter.setTagName(TEST_TAG_NAME);

        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDAO, tagDAO);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void deleteCertificate() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(giftCertificate));
        given(tagDAO.getTagListByGiftCertificateID(TEST_ID)).willReturn(giftTagList);

        giftCertificateService.deleteCertificate(TEST_ID);

        verify(giftCertificateDAO, times(1)).deleteGiftCertificate(TEST_ID);
        verify(tagDAO, times(1)).deleteTag(TEST_TAG_ID);
    }

    @Test
    public void deleteCertificateShouldException() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateService.deleteCertificate(TEST_ID));
    }

    @Test
    public void getGiftCertificateByID() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(giftCertificate));
        GiftCertificateDTO receivedCertificateDTO = giftCertificateService.getGiftCertificateByID(TEST_ID);

        GiftCertificateDTO testedDTO = EntityDTOGiftCertificateMapper.toDTO(giftCertificate);
        assertEquals(testedDTO, receivedCertificateDTO);
    }


    @Test
    public void getGiftCertificateByIDShouldException() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateService.getGiftCertificateByID(TEST_ID));
    }


    @Test
    public void updateCertificate() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.of(giftCertificate));
        given(giftCertificateDAO.updateGiftCertificate(any(), anyInt())).willReturn(giftCertificate);
        given(tagDAO.getTagByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));
        given(tagDAO.getTagListByGiftCertificateID(TEST_ID)).willReturn(giftTagList);

        GiftCertificateDTO receivedDTO = giftCertificateService.updateCertificate(validGiftCertificateDTO, TEST_ID);
        assertEquals(validGiftCertificateDTO, receivedDTO);
    }

    @Test
    public void updateCertificateShouldException() {
        given(giftCertificateDAO.getGiftCertificateByID(TEST_ID)).willReturn(Optional.empty());

        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCertificateService.updateCertificate(any(), TEST_ID));
    }

    @Test
    public void createGiftCertificate() {
        given(giftCertificateDAO.createGiftCertificate(any())).willReturn(giftCertificate);
        given(tagDAO.getTagByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));
        given(tagDAO.getTagListByGiftCertificateID(TEST_ID)).willReturn(giftTagList);

        GiftCertificateDTO receivedDTO = giftCertificateService.createGiftCertificate(validGiftCertificateDTO);

        assertEquals(TEST_NAME, receivedDTO.getName());
        assertEquals(TEST_DESCRIPTION, receivedDTO.getDescription());
        assertEquals(TEST_PRICE, receivedDTO.getPrice());
        assertEquals(TEST_DURATION, receivedDTO.getDuration());
        assertEquals(tagNamesList, receivedDTO.getTagNames());
    }

    @Test
    public void createGiftCertificateShouldException() {
        assertThrows(GiftCertificateDataValidationException.class,
                () -> giftCertificateService.createGiftCertificate(notFullyValuedGiftCertificateDTO));
    }

    @Test
    public void getCertificates() {
        given(giftCertificateDAO.getGiftCertificates()).willReturn(giftCertificateList);
        given(tagDAO.getTagListByGiftCertificateID(TEST_ID)).willReturn(giftTagList);

        List<GiftCertificateDTO> receivedDTOList = giftCertificateService.getCertificates();
        List<GiftCertificateDTO> testDTOList = EntityDTOGiftCertificateMapper.toDTO(giftCertificateList);

        assertIterableEquals(testDTOList, receivedDTOList);
    }

    @Test
    public void getCertificatesWithEmptyQueryParameter() {
        giftCertificateService.getCertificates(emptyQueryParameter);

        verify(giftCertificateDAO).getGiftCertificates();
    }

    @Test
    public void getCertificatesByQueryParameter() {
        given(giftCertificateDAO.getGiftCertificates(any())).willReturn(giftCertificateList);
        List<GiftCertificateDTO> giftCertificateDTO = giftCertificateService.getCertificates(queryParameter);


    }
}