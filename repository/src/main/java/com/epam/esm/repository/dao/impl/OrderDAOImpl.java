package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.OrderDAO;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.Order_;
import com.epam.esm.repository.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements OrderDAO {

    /**
     * An object of {@link EntityManager} that is being injected.
     */
    @PersistenceContext
    private EntityManager entityManager;

    private static final int PAGE_NUMBER_OFFSET = 1;

    /**
     * Connects to database and returns all Users.
     *
     * @param userID is User ID value.
     * @return List of all {@link Order} entities from database.
     */
    public List<Order> getOrdersByUserID(int userID) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Order> orderCriteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = orderCriteriaQuery.from(Order.class);
        orderCriteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Order_.USER), userID));

        return entityManager.createQuery(orderCriteriaQuery).getResultList();
    }

    /**
     * Connects to database and returns User by ID.
     *
     * @param id is Order ID value.
     * @return Optional of {@link Order} entity from database.
     */
    public Optional<Order> getOrder(int id) {
        Order order = entityManager.find(Order.class, id);

        return Optional.ofNullable(order);
    }

    /**
     * Connects to database and add an new Order.
     *
     * @param order {@link Order} is entity with data for creating Order.
     * @return Created {@link Order} entity from database
     */
    public Order createOrder(Order order) {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        order.setDate(currentLocalDateTime);

        entityManager.persist(order);

        return order;
    }

    /**
     * Connects to database and returns all Orders.
     * @param page is page number
     * @param size is page size
     * @return List of all {@link Order} entities from database.
     */
    @Override
    public List<Order> getOrders(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Order> orderQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = orderQuery.from(Order.class);
        orderQuery.select(root);

        int itemsOffset = (page - PAGE_NUMBER_OFFSET) * size;
        return entityManager.createQuery(orderQuery).setFirstResult(itemsOffset).setMaxResults(size).getResultList();
    }
}
