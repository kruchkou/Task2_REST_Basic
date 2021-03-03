package com.epam.esm.repository.dao;

import com.epam.esm.repository.model.entity.Role;

public interface RoleDao {

    Role getRoleByName(String name);

}
