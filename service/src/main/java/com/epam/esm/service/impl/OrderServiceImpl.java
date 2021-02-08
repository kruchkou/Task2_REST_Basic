package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.OrderDAO;
import com.epam.esm.repository.dao.UserDAO;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.exception.impl.OrderNotFoundException;
import com.epam.esm.service.exception.impl.UserNotFoundException;
import com.epam.esm.service.model.dto.OrderDTO;
import com.epam.esm.service.model.util.CreateOrderParameter;
import com.epam.esm.service.util.mapper.EntityDTOOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link OrderService}. Interface provides methods to interact with OrderDAO.
 * Methods should transforms received information into DAO-accepted data and invoke corresponding methods.
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Format string to provide info by what id Order wasn't found.
     */
    private static final String NOT_FOUND_BY_ID_PARAMETER = "id: %d";

    /**
     * Error message when Order wasn't found by id
     */
    private static final String NO_ORDER_WITH_ID_FOUND = "No order with id: %d found";

    /**
     * Error code when Order wasn't found by id
     */
    private static final String ERROR_CODE_ORDER_BY_ID_NOT_FOUND_FAILED = "0402404%d";

    /**
     * Format string to provide info by what id User wasn't found.
     */
    private static final String USER_NOT_FOUND_BY_ID_PARAMETER = "userID: %d";

    /**
     * Error message when User wasn't found by id
     */
    private static final String NO_USER_WITH_ID_FOUND = "No user with id: %d found";

    /**
     * Error code when User wasn't found by id
     */
    private static final String ERROR_CODE_USER_BY_ID_NOT_FOUND_FAILED = "0422404%d";

    /**
     * Error message when GiftCertificate wasn't found by id
     */
    private static final String NO_GIFT_CERTIFICATE_WITH_ID_FOUND = "No certificate with id: %d found";

    /**
     * Format string to provide info by what id GiftCertificate wasn't found.
     */
    private static final String GIFT_NOT_FOUND_BY_ID_PARAMETER = "giftID: %d";

    /**
     * Error code when GiftCertificate wasn't found by id
     */
    private static final String ERROR_CODE_GIFT_NOT_FOUND_FAILED = "0102404%d";

    /**
     * An object of {@link OrderDAO}
     */
    private final OrderDAO orderDAO;
    /**
     * An object of {@link UserDAO}
     */
    private final UserDAO userDAO;
    /**
     * An object of {@link GiftCertificateDAO}
     */
    private final GiftCertificateDAO giftCertificateDAO;

    /**
     * Public constructor that receives orderDAO
     *
     * @param orderDAO           is {@link OrderDAO} interface providing DAO methods.
     * @param userDAO            is {@link UserDAO} interface providing DAO methods.
     * @param giftCertificateDAO is {@link GiftCertificateDAO} interface providing DAO methods.
     */
    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, UserDAO userDAO, GiftCertificateDAO giftCertificateDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.giftCertificateDAO = giftCertificateDAO;
    }

    @Override
    public List<OrderDTO> getOrdersByUserID(int userID) {
        List<Order> orderList = orderDAO.getOrdersByUserID(userID);

        return EntityDTOOrderMapper.toDTO(orderList);
    }

    /**
     * Invokes DAO method to get Order with provided id.
     *
     * @param id is id of order to be returned.
     * @return {@link OrderDTO} object with order data.
     * @throws OrderNotFoundException if no Order with provided id founded
     */
    @Override
    public OrderDTO getOrder(int id) {
        Optional<Order> optionalOrder = orderDAO.getOrder(id);

        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException(
                String.format(NO_ORDER_WITH_ID_FOUND, id),
                String.format(ERROR_CODE_ORDER_BY_ID_NOT_FOUND_FAILED, id),
                String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDTOOrderMapper.toDTO(order);
    }

    /**
     * Connects to database and add an new Order.
     *
     * @param createOrderParameter is {@link CreateOrderParameter} object with data provided
     * @return Created {@link OrderDTO} object with Order data.
     * @throws UserNotFoundException            if no User with provided userID founded
     * @throws GiftCertificateByParameterNotFoundException if GiftCertificate with provided giftID founded
     */
    @Transactional
    public OrderDTO createOrder(CreateOrderParameter createOrderParameter) {
        Integer userID = createOrderParameter.getUserID();
        Optional<User> userOptional = userDAO.getUser(createOrderParameter.getUserID());

        User user = userOptional.orElseThrow(() -> new UserNotFoundException(
                String.format(NO_USER_WITH_ID_FOUND, userID),
                String.format(ERROR_CODE_USER_BY_ID_NOT_FOUND_FAILED, userID),
                String.format(USER_NOT_FOUND_BY_ID_PARAMETER, userID)));

        List<GiftCertificate> giftCertificateList = new ArrayList<>();

        int orderPrice = 0;

        for (Integer giftID : createOrderParameter.getGifts()) {
            Optional<GiftCertificate> giftOptional = giftCertificateDAO.getGiftCertificateByID(giftID);

            GiftCertificate giftCertificate = giftOptional.orElseThrow(() -> new GiftCertificateByParameterNotFoundException(
                    String.format(NO_GIFT_CERTIFICATE_WITH_ID_FOUND, giftID),
                    String.format(ERROR_CODE_GIFT_NOT_FOUND_FAILED, giftID),
                    String.format(GIFT_NOT_FOUND_BY_ID_PARAMETER, giftID)));

            orderPrice += giftCertificate.getPrice();

            giftCertificateList.add(giftCertificate);
        }

        Order order = new Order();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        order.setUser(user);
        order.setGiftList(giftCertificateList);
        order.setPrice(orderPrice);
        order.setDate(currentLocalDateTime);

        Order resultOrder = orderDAO.createOrder(order);
        return EntityDTOOrderMapper.toDTO(resultOrder);
    }

    /**
     * Invokes DAO method to get List of all Orders from database.
     *
     * @return List of {@link OrderDTO} objects with order data.
     */
    @Override
    public List<OrderDTO> getOrders() {
        List<Order> orderList = orderDAO.getOrders();

        return EntityDTOOrderMapper.toDTO(orderList);
    }

}
