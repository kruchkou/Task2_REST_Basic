package com.epam.esm.repository;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer>,
        JpaSpecificationExecutor<GiftCertificate> {

    /**
     * Connects to database and returns Tag by name.
     *
     * @param name is Tag name value.
     * @return Optional of {@link Tag} entity from database.
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Connects to database and returns Tag by ID.
     *
     * @param id is Gift Certificate ID value.
     * @return Optional of {@link GiftCertificate} entity from database.
     */
    Optional<GiftCertificate> findById(int id);

    /**
     * Connects to database and deletes Gift Certificate with provided ID
     *
     * @param id is Tag ID value.
     */
    void deleteById(int id);

}
