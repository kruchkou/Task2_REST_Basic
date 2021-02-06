package com.epam.esm.repository.model.util;

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
    void successBuild() {
        Filter goodLowerFilter = FilterBuilder.build(GOOD_LOWER_PARAMETER_INPUT);
        Filter goodHighFilter = FilterBuilder.build(GOOD_HIGH_PARAMETER_INPUT);
        Filter goodRandomHeightFilter = FilterBuilder.build(GOOD_RANDOM_HEIGHT_PARAMETER_INPUT);
        Filter onlyValueFilter = FilterBuilder.build(ONLY_VALUE_PARAMETER_INPUT);

        assertEquals(FilterType.LTE,goodLowerFilter.getType());
        assertEquals(FilterType.LT,goodHighFilter.getType());
        assertEquals(FilterType.GTE,goodRandomHeightFilter.getType());
        assertEquals(FilterType.EQUALS,onlyValueFilter.getType());

        assertEquals(10,goodLowerFilter.getValue());
        assertEquals(10,goodHighFilter.getValue());
        assertEquals(10,goodRandomHeightFilter.getValue());
        assertEquals(10,onlyValueFilter.getValue());
    }

    @Test
    void buildShouldThrowException() {
        assertThrows(RuntimeException.class, () -> FilterBuilder.build(WRONG_VALUE_INPUT));
        assertThrows(RuntimeException.class, () -> FilterBuilder.build(WRONG_PARAMETER_INPUT));
        assertThrows(RuntimeException.class, () -> FilterBuilder.build(RANDOM_PARAMETER_INPUT));
    }
}