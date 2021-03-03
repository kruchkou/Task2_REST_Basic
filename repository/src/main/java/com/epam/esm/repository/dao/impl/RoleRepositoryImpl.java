package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.RoleDao;
import com.epam.esm.repository.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositoryImpl extends JpaRepository<Role, Integer>, RoleDao {

    Role getRoleByName(String name);

}
