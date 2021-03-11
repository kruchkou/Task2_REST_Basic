package com.epam.esm.repository;

import com.epam.esm.repository.model.entity.Tag;

public interface CustomizedTagRepository {

    /**
     * Connects to database and returns the most widely used tag of a user with the highest cost of all orders.
     *
     * @return {@link Tag} object with tag data.
     */
    Tag getMostWidelyUsedTag(int userID);

}
