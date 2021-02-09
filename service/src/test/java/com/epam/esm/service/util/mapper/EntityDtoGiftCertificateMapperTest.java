package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDtoGiftCertificateMapperTest {

    private static final Integer TEST_ID = 3;
    private static final String TEST_NAME = "test name";
    private static final String TEST_DESCRIPTION = "test description";
    private static final Integer TEST_PRICE = 10;
    private static final Integer TEST_DURATION = 20;
    private static final LocalDateTime TEST_CREATE_DATE_LOCAL_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME = LocalDateTime.now();
    private GiftCertificate giftCertificate;
    private GiftCertificateDto giftCertificateDto;

    @BeforeEach
    public void init() {
        giftCertificate = new GiftCertificate();
        giftCertificateDto = new GiftCertificateDto();

        giftCertificate.setId(TEST_ID);
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
        giftCertificate.setCreateDate(TEST_CREATE_DATE_LOCAL_DATE_TIME);
        giftCertificate.setLastUpdateDate(TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME);

        giftCertificateDto.setId(TEST_ID);
        giftCertificateDto.setName(TEST_NAME);
        giftCertificateDto.setDescription(TEST_DESCRIPTION);
        giftCertificateDto.setPrice(TEST_PRICE);
        giftCertificateDto.setDuration(TEST_DURATION);
        giftCertificateDto.setCreateDate(TEST_CREATE_DATE_LOCAL_DATE_TIME);
        giftCertificateDto.setLastUpdateDate(TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME);
    }

    @Test
    public void shouldConvertToEntity() {
        final GiftCertificate testedGiftCertificate = EntityDtoGiftCertificateMapper.toEntity(giftCertificateDto);

        assertEquals(TEST_NAME, testedGiftCertificate.getName());
        assertEquals(TEST_DESCRIPTION, testedGiftCertificate.getDescription());
        assertEquals(TEST_PRICE, testedGiftCertificate.getPrice());
        assertEquals(TEST_DURATION, testedGiftCertificate.getDuration());
        assertEquals(TEST_CREATE_DATE_LOCAL_DATE_TIME, testedGiftCertificate.getCreateDate());
        assertEquals(TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME, testedGiftCertificate.getLastUpdateDate());
    }

    @Test
    public void shouldConvertToDto() {
        final GiftCertificateDto testedGiftCertificateDto = EntityDtoGiftCertificateMapper.toDto(giftCertificate);

        assertEquals(TEST_NAME, testedGiftCertificateDto.getName());
        assertEquals(TEST_DESCRIPTION, testedGiftCertificateDto.getDescription());
        assertEquals(TEST_PRICE, testedGiftCertificateDto.getPrice());
        assertEquals(TEST_DURATION, testedGiftCertificateDto.getDuration());
        assertEquals(TEST_CREATE_DATE_LOCAL_DATE_TIME, testedGiftCertificateDto.getCreateDate());
        assertEquals(TEST_LAST_UPDATE_DATE_LOCAL_DATE_TIME, testedGiftCertificateDto.getLastUpdateDate());
    }
}