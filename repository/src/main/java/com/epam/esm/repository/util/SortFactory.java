package com.epam.esm.repository.util;

import com.epam.esm.repository.model.util.Sort;
import com.epam.esm.repository.model.util.SortBy;
import com.epam.esm.repository.model.util.SortOrientation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SortFactory {

    public static Sort createSort(String parameter) {
        String PARAMETER_VALUE_REGEX = "([\\w]*):([\\w]*)";
        int SORT_BY_GROUP_INDEX = 1;
        int SORT_ORIENTATION_GROUP_INDEX = 2;

        Pattern pattern = Pattern.compile(PARAMETER_VALUE_REGEX);
        Matcher matcher = pattern.matcher(parameter);

        Sort sort = new Sort();

        if (!matcher.matches()) {
            sort.setSortBy(SortBy.valueOf(parameter.toUpperCase()));
            sort.setSortOrientation(SortOrientation.ASC);

        } else {
            String sortByValue = matcher.group(SORT_BY_GROUP_INDEX).toUpperCase();

            sort.setSortBy(SortBy.valueOf(sortByValue));

            String sortOrientationValue = matcher.group(SORT_ORIENTATION_GROUP_INDEX).toUpperCase();
            sort.setSortOrientation(SortOrientation.valueOf(sortOrientationValue));
        }

        return sort;
    }
}
