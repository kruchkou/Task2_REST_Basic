package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.config.TestConfig;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
class GiftCertificateDaoImplTest {

    private static final int TEST_ID = 1;
    private static final int NOT_EXIST_ID = 15;
    private static final String TEST_NAME = "Cert";
    private static final String TEST_DESC = "This is";
    private static final String TEST_FIRST_TAG_NAME = "first";
    private static final String TEST_SECOND_TAG_NAME = "second";
    private static final String TEST_THIRD_TAG_NAME = "third";
    private static final int TEST_PRICE = 300;
    private static final int NEW_TEST_PRICE = 500;
    private static final int TEST_DURATION = 30;

    private GiftCertificate giftCertificate;
    private GetGiftCertificateQueryParameter firstTagGiftParameter;
    private GetGiftCertificateQueryParameter secondTagGiftParameter;
    private GetGiftCertificateQueryParameter thirdTagGiftParameter;
    private GetGiftCertificateQueryParameter byNameGiftParameter;
    private GetGiftCertificateQueryParameter byDescGiftParameter;
    private List<String> firstTagList;
    private List<String> secondTagList;
    private List<String> thirdTagList;
    private Page page;

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    public void setUp() {
        page = new Page();

        firstTagList = new ArrayList<>();
        firstTagList.add(TEST_FIRST_TAG_NAME);

        secondTagList =  new ArrayList<>();
        secondTagList.add(TEST_SECOND_TAG_NAME);

        thirdTagList =  new ArrayList<>();
        thirdTagList.add(TEST_THIRD_TAG_NAME);

        giftCertificate = new GiftCertificate();
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESC);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);

        firstTagGiftParameter = new GetGiftCertificateQueryParameter(
                null, null, null, null,  null, firstTagList);
        secondTagGiftParameter = new GetGiftCertificateQueryParameter(
                null, null, null,null, null, secondTagList);
        thirdTagGiftParameter = new GetGiftCertificateQueryParameter(
                null, null, null,null, null, thirdTagList);

        byNameGiftParameter = new GetGiftCertificateQueryParameter(
                TEST_NAME, null, null, null,null,null);
        byDescGiftParameter = new GetGiftCertificateQueryParameter(
                null, TEST_DESC, null, null,null,null);

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @Transactional
    public void createGiftCertificate() {
        GiftCertificate savedGift = giftCertificateDao.createGiftCertificate(giftCertificate);

        assertNotNull(savedGift);
        assertEquals(TEST_NAME, savedGift.getName());
        assertEquals(TEST_DESC, savedGift.getDescription());
        assertEquals(TEST_PRICE, savedGift.getPrice());
        assertEquals(TEST_DURATION, savedGift.getDuration());
    }

    @Test
    @Transactional
    public void updateGiftCertificate() {
        GiftCertificate gift = giftCertificateDao.getGiftCertificateByID(TEST_ID).get();
        LocalDateTime prevLastUpdateDate = gift.getLastUpdateDate();

        GiftCertificate giftCertificateForUpdate = new GiftCertificate();

        giftCertificateForUpdate.setPrice(NEW_TEST_PRICE);

        GiftCertificate updatedGift = giftCertificateDao.updateGiftCertificate(giftCertificateForUpdate, TEST_ID);

        assertEquals(NEW_TEST_PRICE, updatedGift.getPrice());
        assertNotEquals(prevLastUpdateDate, updatedGift.getLastUpdateDate());
    }

    @Test
    public void getCertificateByID() {
        Optional<GiftCertificate> existGiftCertificate = giftCertificateDao.getGiftCertificateByID(TEST_ID);
        Optional<GiftCertificate> notExistGiftCertificate = giftCertificateDao.getGiftCertificateByID(NOT_EXIST_ID);

        assertTrue(existGiftCertificate.isPresent());
        assertFalse(notExistGiftCertificate.isPresent());
    }

    @Test
    public void getCertificates() {
        final int EXIST_GIFT_QUANTITY = 2;

        final List<GiftCertificate> giftCertificateList = giftCertificateDao.getGiftCertificates(page.getPage(),
                page.getSize());

        assertNotNull(giftCertificateList);
        assertEquals(EXIST_GIFT_QUANTITY, giftCertificateList.size());
    }

    @Test
    public void getCertificatesByTagName() {
        final int GIFTS_QUANTITY_WITH_FIRST_TAG = 1;
        final int GIFTS_QUANTITY_WITH_SECOND_TAG = 2;
        final int GIFTS_QUANTITY_WITH_THIRD_TAG = 0;

        List<GiftCertificate> firstGiftCertificateList = giftCertificateDao.getGiftCertificates(firstTagGiftParameter);
        List<GiftCertificate> secondGiftCertificateList = giftCertificateDao.getGiftCertificates(secondTagGiftParameter);
        List<GiftCertificate> thirdGiftCertificateList = giftCertificateDao.getGiftCertificates(thirdTagGiftParameter);

        assertEquals(GIFTS_QUANTITY_WITH_FIRST_TAG, firstGiftCertificateList.size());
        assertEquals(GIFTS_QUANTITY_WITH_SECOND_TAG, secondGiftCertificateList.size());
        assertEquals(GIFTS_QUANTITY_WITH_THIRD_TAG, thirdGiftCertificateList.size());
    }

    @Test
    public void getCertificatesByNameOrDescription() {
        final int GIFT_QUANTITY_WITH_SEARCH_NAME = 2;
        final int GIFT_QUANTITY_WITH_DESC_NAME = 1;

        List<GiftCertificate> giftCertificateListByName = giftCertificateDao.getGiftCertificates(byNameGiftParameter);
        List<GiftCertificate> giftCertificateListByDesc = giftCertificateDao.getGiftCertificates(byDescGiftParameter);

        assertEquals(GIFT_QUANTITY_WITH_SEARCH_NAME, giftCertificateListByName.size());
        assertEquals(GIFT_QUANTITY_WITH_DESC_NAME, giftCertificateListByDesc.size());
    }

}