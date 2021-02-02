package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.service.model.dto.GiftCertificateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDTOGiftCertificateMapperTest {

    private static final Integer TEST_ID = 3;
    private static final String TEST_NAME = "test name";
    private static final String TEST_DESCRIPTION = "test description";
    private static final Integer TEST_PRICE = 10;
    private static final Integer TEST_DURATION = 20;
    private static final LocalDateTime TEST_CREATE_DATE_LOCAL_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME = LocalDateTime.now();
    private GiftCertificate giftCertificate;
    private GiftCertificateDTO giftCertificateDTO;

    @BeforeEach
    public void init() {
        giftCertificate = new GiftCertificate();
        giftCertificateDTO = new GiftCertificateDTO();

        giftCertificate.setId(TEST_ID);
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
        giftCertificate.setCreateDate(TEST_CREATE_DATE_LOCAL_DATE_TIME);
        giftCertificate.setLastUpdateDate(TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME);

        giftCertificateDTO.setId(TEST_ID);
        giftCertificateDTO.setName(TEST_NAME);
        giftCertificateDTO.setDescription(TEST_DESCRIPTION);
        giftCertificateDTO.setPrice(TEST_PRICE);
        giftCertificateDTO.setDuration(TEST_DURATION);
        giftCertificateDTO.setCreateDate(TEST_CREATE_DATE_LOCAL_DATE_TIME);
        giftCertificateDTO.setLastUpdateDate(TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME);
    }

    @Test
    public void shouldConvertToEntity() {
        final GiftCertificate testedGiftCertificate = EntityDTOGiftCertificateMapper.toEntity(giftCertificateDTO);

        assertEquals(TEST_NAME, testedGiftCertificate.getName());
        assertEquals(TEST_DESCRIPTION, testedGiftCertificate.getDescription());
        assertEquals(TEST_PRICE, testedGiftCertificate.getPrice());
        assertEquals(TEST_DURATION, testedGiftCertificate.getDuration());
        assertEquals(TEST_CREATE_DATE_LOCAL_DATE_TIME, testedGiftCertificate.getCreateDate());
        assertEquals(TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME, testedGiftCertificate.getLastUpdateDate());
    }

    @Test
    public void shouldConvertToDTO() {
        final GiftCertificateDTO testedGiftCertificateDTO = EntityDTOGiftCertificateMapper.toDTO(giftCertificate);

        assertEquals(TEST_NAME, testedGiftCertificateDTO.getName());
        assertEquals(TEST_DESCRIPTION, testedGiftCertificateDTO.getDescription());
        assertEquals(TEST_PRICE, testedGiftCertificateDTO.getPrice());
        assertEquals(TEST_DURATION, testedGiftCertificateDTO.getDuration());
        assertEquals(TEST_CREATE_DATE_LOCAL_DATE_TIME, testedGiftCertificateDTO.getCreateDate());
        assertEquals(TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME, testedGiftCertificateDTO.getLastUpdateDate());
    }
}