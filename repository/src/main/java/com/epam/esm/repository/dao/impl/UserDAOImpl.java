package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.UserDAO;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    /**
     * An object of {@link EntityManager} that is being injected.
     */
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * Connects to database and returns all Users.
     *
     * @return List of all {@link User} entities from database.
     */
    @Override
    public List<User> getUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> userQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = userQuery.from(User.class);
        userQuery.select(root);

        return entityManager.createQuery(userQuery).getResultList();
    }

    /**
     * Connects to database and returns User by ID.
     *
     * @param id is User ID value.
     * @return Optional of {@link User} entity from database.
     */
    @Override
    public Optional<User> getUser(int id) {
        User user = entityManager.find(User.class, id);

        return Optional.ofNullable(user);
    }
}
