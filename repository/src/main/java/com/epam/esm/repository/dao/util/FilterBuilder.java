package com.epam.esm.repository.dao.util;

import com.epam.esm.repository.model.util.Filter;
import com.epam.esm.repository.model.util.FilterType;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FilterBuilder {

    public static List<Filter> build(List<String> parameters) {
        return parameters.stream().map(FilterBuilder::build).collect(Collectors.toList());
    }

    public static Filter build(String parameter) {
        String PARAMETER_VALUE_REGEX = "([\\w]*):(.*)";
        int FILTER_TYPE_GROUP_INDEX = 1;
        int FILTER_VALUE_GROUP_INDEX = 2;

        Pattern pattern = Pattern.compile(PARAMETER_VALUE_REGEX);
        Matcher matcher = pattern.matcher(parameter);

        Filter filter = new Filter();

        if (!matcher.matches()) {
            filter.setType(FilterType.EQUALS);
            filter.setValue(Integer.parseInt(parameter));

        } else {
            String filterTypeName = matcher.group(FILTER_TYPE_GROUP_INDEX).toUpperCase();

            filter.setType(FilterType.valueOf(filterTypeName));

            String stringFilterValue = matcher.group(FILTER_VALUE_GROUP_INDEX);
            filter.setValue(Integer.parseInt(stringFilterValue));
        }

        return filter;
    }
    
}
