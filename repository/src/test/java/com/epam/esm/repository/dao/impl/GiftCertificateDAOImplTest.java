package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.util.GetGiftCertificateSQLBuilder;
import com.epam.esm.repository.dao.util.UpdateGiftCertificateSQLBuilder;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.GiftCertificateSQL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateDAOImplTest {
    private final static int TEST_ID = 1;
    private final static int NOT_EXIST_ID = 15;
    private final static String TEST_NAME = "Cert";
    private final static String TEST_DESC = "This is";
    private final static int TEST_PRICE = 300;
    private final static int NEW_TEST_PRICE = 500;
    private final static int TEST_DURATION = 30;
    private final static String FIRST_TEST_TAG_NAME = "first";
    private final static String SECOND_TEST_TAG_NAME = "second";
    private final static String THIRD_TEST_TAG_NAME = "third";

    private final static GetGiftCertificateSQLBuilder getGiftSQLBuilder = GetGiftCertificateSQLBuilder.getInstance();

    private GiftCertificate giftCertificate;
    private EmbeddedDatabase embeddedDatabase;
    private GiftCertificateDAO giftCertificateDAO;

    private GiftCertificateSQL prepareGiftCertificateSQL(String name, String tagName, String description, String sortBy,
                                                         String sortOrientation) {
        GetGiftCertificateQueryParameter giftParameter = new GetGiftCertificateQueryParameter();

        giftParameter.setName(name);
        giftParameter.setTagName(tagName);
        giftParameter.setDescription(description);
        giftParameter.setSortBy(sortBy);
        giftParameter.setSortOrientation(sortOrientation);

        return getGiftSQLBuilder.build(giftParameter);
    }

    @BeforeEach
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        giftCertificateDAO = new GiftCertificateDAOImpl(embeddedDatabase);

        giftCertificate = new GiftCertificate();
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESC);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    public void createGiftCertificate() {
        GiftCertificate savedGift = giftCertificateDAO.createGiftCertificate(giftCertificate);

        assertNotNull(savedGift);
        assertEquals(TEST_NAME, savedGift.getName());
        assertEquals(TEST_DESC, savedGift.getDescription());
        assertEquals(TEST_PRICE, savedGift.getPrice());
        assertEquals(TEST_DURATION, savedGift.getDuration());
    }

    @Test
    public void deleteGiftCertificate() {
        giftCertificateDAO.deleteGiftCertificate(TEST_ID);

        Optional<GiftCertificate> giftCertificate = giftCertificateDAO.getGiftCertificateByID(TEST_ID);

        assertFalse(giftCertificate.isPresent());
    }

    @Test
    public void updateGiftCertificate() {
        GiftCertificate gift = giftCertificateDAO.getGiftCertificateByID(TEST_ID).get();

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(NEW_TEST_PRICE);
        giftCertificate.setId(TEST_ID);
        giftCertificate.setLastUpdateDate(Instant.now());

        GiftCertificateSQL updateRequest = UpdateGiftCertificateSQLBuilder.getInstance().build(giftCertificate);

        GiftCertificate updatedGift = giftCertificateDAO.updateGiftCertificate(updateRequest, TEST_ID);

        assertEquals(NEW_TEST_PRICE, updatedGift.getPrice());
        assertNotEquals(gift.getLastUpdateDate(), updatedGift.getLastUpdateDate());
    }

    @Test
    public void getCertificateByID() {


        Optional<GiftCertificate> existGiftCertificate = giftCertificateDAO.getGiftCertificateByID(TEST_ID);
        Optional<GiftCertificate> notExistGiftCertificate = giftCertificateDAO.getGiftCertificateByID(NOT_EXIST_ID);

        assertTrue(existGiftCertificate.isPresent());
        assertFalse(notExistGiftCertificate.isPresent());
    }

    @Test
    public void getCertificates() {
        final int EXIST_GIFT_QUANTITY = 2;
        final List<GiftCertificate> giftCertificateList = giftCertificateDAO.getGiftCertificates();

        assertNotNull(giftCertificateList);
        assertEquals(EXIST_GIFT_QUANTITY, giftCertificateList.size());
    }

    @Test
    public void getCertificatesByTagName() {
        final int GIFTS_QUANTITY_WITH_FIRST_TAG = 1;
        final int GIFTS_QUANTITY_WITH_SECOND_TAG = 2;
        final int GIFTS_QUANTITY_WITH_THIRD_TAG = 0;

        GiftCertificateSQL firstTagGiftSQL = prepareGiftCertificateSQL(
                null, FIRST_TEST_TAG_NAME, null, null, null);
        GiftCertificateSQL secondTagGiftSQL = prepareGiftCertificateSQL(
                null, SECOND_TEST_TAG_NAME, null, null, null);
        GiftCertificateSQL thirdTagGiftSQL = prepareGiftCertificateSQL(
                null, THIRD_TEST_TAG_NAME, null, null, null);


        List<GiftCertificate> firstGiftCertificateList = giftCertificateDAO.getGiftCertificates(firstTagGiftSQL);
        List<GiftCertificate> secondGiftCertificateList = giftCertificateDAO.getGiftCertificates(secondTagGiftSQL);
        List<GiftCertificate> thirdGiftCertificateList = giftCertificateDAO.getGiftCertificates(thirdTagGiftSQL);

        assertEquals(GIFTS_QUANTITY_WITH_FIRST_TAG, firstGiftCertificateList.size());
        assertEquals(GIFTS_QUANTITY_WITH_SECOND_TAG, secondGiftCertificateList.size());
        assertEquals(GIFTS_QUANTITY_WITH_THIRD_TAG, thirdGiftCertificateList.size());
    }

    @Test
    public void getCertificatesByNameOrDescription() {
        final int GIFT_QUANTITY_WITH_SEARCH_NAME = 2;
        final int GIFT_QUANTITY_WITH_DESC_NAME = 1;

        GiftCertificateSQL byNameGiftSQL = prepareGiftCertificateSQL(
                TEST_NAME, null, null, null, null);
        GiftCertificateSQL byDescGiftSQL = prepareGiftCertificateSQL(
                null, null, TEST_DESC, null, null);

        List<GiftCertificate> giftCertificateListByName = giftCertificateDAO.getGiftCertificates(byNameGiftSQL);
        List<GiftCertificate> giftCertificateListByDesc = giftCertificateDAO.getGiftCertificates(byDescGiftSQL);

        assertEquals(GIFT_QUANTITY_WITH_SEARCH_NAME, giftCertificateListByName.size());
        assertEquals(GIFT_QUANTITY_WITH_DESC_NAME, giftCertificateListByDesc.size());
    }

}