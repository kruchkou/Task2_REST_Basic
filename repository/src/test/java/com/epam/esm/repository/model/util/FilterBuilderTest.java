package com.epam.esm.repository.model.util;

import com.epam.esm.repository.util.FilterFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilterBuilderTest {

    private static final String GOOD_LOWER_PARAMETER_INPUT = "lte:10";
    private static final String GOOD_HIGH_PARAMETER_INPUT = "LT:10";
    private static final String GOOD_RANDOM_HEIGHT_PARAMETER_INPUT = "GtE:10";
    private static final String ONLY_VALUE_PARAMETER_INPUT = "10";

    private static final String WRONG_VALUE_INPUT = "lte:10b";
    private static final String WRONG_PARAMETER_INPUT = "asd:10";
    private static final String RANDOM_PARAMETER_INPUT = "asg1252gd";

    @Test
    public void successBuild() {
        Filter goodLowerFilter = FilterFactory.createFilter(GOOD_LOWER_PARAMETER_INPUT);
        Filter goodHighFilter = FilterFactory.createFilter(GOOD_HIGH_PARAMETER_INPUT);
        Filter goodRandomHeightFilter = FilterFactory.createFilter(GOOD_RANDOM_HEIGHT_PARAMETER_INPUT);
        Filter onlyValueFilter = FilterFactory.createFilter(ONLY_VALUE_PARAMETER_INPUT);

        assertEquals(FilterType.LTE, goodLowerFilter.getType());
        assertEquals(FilterType.LT, goodHighFilter.getType());
        assertEquals(FilterType.GTE, goodRandomHeightFilter.getType());
        assertEquals(FilterType.EQUALS, onlyValueFilter.getType());

        assertEquals(10, goodLowerFilter.getValue());
        assertEquals(10, goodHighFilter.getValue());
        assertEquals(10, goodRandomHeightFilter.getValue());
        assertEquals(10, onlyValueFilter.getValue());
    }

    @Test
    public void buildShouldThrowException() {
        assertThrows(RuntimeException.class, () -> FilterFactory.createFilter(WRONG_VALUE_INPUT));
        assertThrows(RuntimeException.class, () -> FilterFactory.createFilter(WRONG_PARAMETER_INPUT));
        assertThrows(RuntimeException.class, () -> FilterFactory.createFilter(RANDOM_PARAMETER_INPUT));
    }
}