package com.epam.esm.repository.dao.util;

import com.epam.esm.repository.model.util.Filter;
import com.epam.esm.repository.model.util.FilterType;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class FilterBuilder {

    private static final String PARAMETER_VALUE_REGEX = "([\\w]*):(.*)";
    private static final int FILTER_TYPE_GROUP_INDEX = 1;
    private static final int FILTER_VALUE_GROUP_INDEX = 2;

    public static List<Filter> build(List<String> parameters) {
        return parameters.stream().map(FilterBuilder::build).collect(Collectors.toList());
    }

    public static Filter build(String parameter) {


        Pattern pattern = Pattern.compile(PARAMETER_VALUE_REGEX);
        Matcher matcher = pattern.matcher(parameter);

        Filter filter = new Filter();

        if (!matcher.matches()) {
            filter.setType(FilterType.EQUALS);
            filter.setValue(Integer.parseInt(parameter.toUpperCase()));

        } else {
            String filterTypeName = matcher.group(FILTER_TYPE_GROUP_INDEX).toUpperCase();

            filter.setType(FilterType.valueOf(filterTypeName));

            String stringFilterValue = matcher.group(FILTER_VALUE_GROUP_INDEX).toUpperCase();;
            filter.setValue(Integer.parseInt(stringFilterValue));
        }

        return filter;
    }
    
}
