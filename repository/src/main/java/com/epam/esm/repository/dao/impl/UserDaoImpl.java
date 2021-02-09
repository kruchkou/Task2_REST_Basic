package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.UserDao;
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
public class UserDaoImpl implements UserDao {

    /**
     * JPQL query to get User with highest cost of all orders
     */
    private static final String SELECT_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS_JPQL =
            "SELECT orders.user FROM Order orders " +
                    "GROUP BY orders.user ORDER BY SUM(orders.price) DESC";

    /**
     * An object of {@link EntityManager} that is being injected.
     */
    @PersistenceContext
    private EntityManager entityManager;

    private static final int PAGE_NUMBER_OFFSET = 1;

    /**
     * Connects to database and returns all Users.
     *
     * @return List of all {@link User} entities from database.
     */
    @Override
    public List<User> getUsers(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> userQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = userQuery.from(User.class);
        userQuery.select(root);

        int itemsOffset = (page - PAGE_NUMBER_OFFSET) * size;
        return entityManager.createQuery(userQuery).setFirstResult(itemsOffset).setMaxResults(size).getResultList();
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

    /**
     * Invokes Dao method to get user with the highest cost of all orders
     *
     * @return {@link User} object with user data.
     */
    public User getUserWithHighestCostOfAllOrders() {
        return (User) entityManager.createQuery(SELECT_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS_JPQL)
                .setMaxResults(1).getSingleResult();
    }

}
