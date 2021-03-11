package com.epam.esm.repository;

import com.epam.esm.repository.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Connects to database and returns Role by name.
     *
     * @param name is Role name.
     * @return {@link Role} entity from database.
     */
    Role getRoleByName(String name);

}
