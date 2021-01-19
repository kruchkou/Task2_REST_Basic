package com.epam.esm.repository.dao.util;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.util.GiftCertificateSQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateGiftCertificateSQLBuilderTest {

    private final static String CORRECT_SQL = "UPDATE gift_certificate SET name = ?, description = ?, price = ?, " +
            "duration = ?, last_update_date = ? WHERE id = ?";
    private final static String TEST_NAME = "test_name";
    private final static String TEST_DESCRIPTION = "test_desc";
    private final static int TEST_ID = 2;
    private final static int TEST_PRICE = 10;
    private final static int TEST_DURATION = 30;
    private final static int FAKE_PRICE = 20;
    private final static Instant LAST_UPDATE_INSTANT = Instant.now();
    private final static UpdateGiftCertificateSQLBuilder updateGiftCertificateQueryBuilder =
            UpdateGiftCertificateSQLBuilder.getInstance();

    private Object[] correctElements;
    GiftCertificate giftCertificate;

    @BeforeEach
    public void init() {
        correctElements = new Object[]{TEST_NAME, TEST_DESCRIPTION, TEST_PRICE, TEST_DURATION, LAST_UPDATE_INSTANT, TEST_ID};
        giftCertificate = new GiftCertificate();

        giftCertificate.setId(TEST_ID);
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(FAKE_PRICE);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
        giftCertificate.setLastUpdateDate(LAST_UPDATE_INSTANT);
    }

    @Test
    public void testBuildMethod() {
        GiftCertificateSQL testSQL = updateGiftCertificateQueryBuilder
                .build(giftCertificate);

        assertEquals(CORRECT_SQL, testSQL.getRequest());
        assertArrayEquals(correctElements, testSQL.getParams());
    }

}