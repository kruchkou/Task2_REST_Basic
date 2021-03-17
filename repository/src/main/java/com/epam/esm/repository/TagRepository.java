package com.epam.esm.repository;

import com.epam.esm.repository.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag>,
        CustomizedTagRepository {

    /**
     * Connects to database and returns Tag by name.
     *
     * @param name is Tag name value.
     * @return Optional of {@link Tag} entity from database.
     */
    Optional<Tag> findByName(String name);

    /**
     * Connects to database and returns Tag by ID.
     *
     * @param id is Tag ID value.
     * @return Optional of {@link Tag} entity from database.
     */
    Optional<Tag> findById(int id);

    /**
     * Connects to database and deletes Tag with provided ID
     *
     * @param id is Tag ID value.
     */
    void deleteById(int id);

    /**
     * Connects to database and returns Tags by names.
     *
     * @param nameList is List of Tag names.
     * @return List of {@link Tag} entity from database.
     */
    List<Tag> findByNameIn(List<String> nameList);

}
