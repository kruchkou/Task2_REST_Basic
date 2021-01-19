package com.epam.esm.repository.dao.util;

import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.GiftCertificateSQL;
import com.epam.esm.repository.model.util.SortBy;
import com.epam.esm.repository.model.util.SortOrientation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GetGiftCertificateSQLBuilderTest {

    private final static String TEST_NAME = "Cert";
    private final static String TEST_DESCRIPTION = "certificate";
    private final static String TEST_TAG_NAME = "second";

    private final static String CORRECT_BY_NAME_SQL = "SELECT * from gift_certificate gifts WHERE (gifts.name REGEXP ?) " +
            "ORDER BY gifts.name ASC";
    private final static String CORRECT_BY_TAG_NAME_SQL = "SELECT * from gift_certificate gifts " +
            "INNER JOIN gift_tag link ON gifts.id = link.gift " +
            "INNER JOIN tag tags ON link.tag = tags.id " +
            "WHERE (tags.name = ? AND description REGEXP ?) " +
            "ORDER BY last_update_date DESC";

    private final static Object[] CORRECT_BY_NAME_ELEMENTS = new Object[]{TEST_NAME};
    private final static Object[] CORRECT_BY_TAG_NAME_ELEMENTS = new Object[]{TEST_TAG_NAME, TEST_DESCRIPTION};

    private GetGiftCertificateQueryParameter prepareQueryParameter(String name, String tagName, String description,
                                                                   String sortBy, String sortOrientation) {
        GetGiftCertificateQueryParameter giftParameter = new GetGiftCertificateQueryParameter();

        giftParameter.setName(name);
        giftParameter.setTagName(tagName);
        giftParameter.setDescription(description);
        giftParameter.setSortBy(sortBy);
        giftParameter.setSortOrientation(sortOrientation);

        return giftParameter;
    }

    @Test
    public void testBuildMethod() {
        GetGiftCertificateQueryParameter byNameQueryParameter = prepareQueryParameter(TEST_NAME, null, null,
                SortBy.NAME.toString(), SortOrientation.ASC.toString());
        GetGiftCertificateQueryParameter byTagNameQueryParameter = prepareQueryParameter(null, TEST_TAG_NAME,
                TEST_DESCRIPTION, SortBy.DATE.toString(), SortOrientation.DESC.toString());

        GiftCertificateSQL byNameSQL = GetGiftCertificateSQLBuilder.getInstance().build(byNameQueryParameter);
        GiftCertificateSQL byTagNameSQL = GetGiftCertificateSQLBuilder.getInstance().build(byTagNameQueryParameter);


        assertEquals(CORRECT_BY_NAME_SQL, byNameSQL.getRequest());
        assertArrayEquals(CORRECT_BY_NAME_ELEMENTS, byNameSQL.getParams());
        assertEquals(CORRECT_BY_TAG_NAME_SQL, byTagNameSQL.getRequest());
        assertArrayEquals(CORRECT_BY_TAG_NAME_ELEMENTS, byTagNameSQL.getParams());
    }

}