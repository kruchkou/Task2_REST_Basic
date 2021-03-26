package com.epam.esm.service.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.impl.DataValidationException;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.exception.impl.OrderNotFoundException;
import com.epam.esm.service.exception.impl.UserNotFoundException;
import com.epam.esm.service.model.dto.OrderDto;
import com.epam.esm.service.model.util.CreateOrderParameter;
import com.epam.esm.service.util.mapper.EntityDtoOrderMapper;
import com.epam.esm.service.util.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link OrderService}. Interface provides methods to interact with OrderRepository.
 * Methods should transforms received information into Repository-accepted data and invoke corresponding methods.
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
     * Error message when data failed validation
     */
    private static final String DATA_VALIDATION_EXCEPTION = "Data didn't passed validation";

    /**
     * Error code when data failed validation
     */
    private static final String ERROR_CODE_ORDER_VALIDATION_FAILED = "0401";

    /**
     * An object of {@link OrderRepository}
     */
    private final OrderRepository orderRepository;
    /**
     * An object of {@link UserRepository}
     */
    private final UserRepository userRepository;
    /**
     * An object of {@link GiftCertificateRepository}
     */
    private final GiftCertificateRepository giftCertificateRepository;

    /**
     * Public constructor that receives orderRepository
     *
     * @param orderRepository           is {@link OrderRepository} interface providing Repository methods.
     * @param userRepository            is {@link UserRepository} interface providing Repository methods.
     * @param giftCertificateRepository is {@link GiftCertificateRepository} interface providing Repository methods.
     */
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, GiftCertificateRepository giftCertificateRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public List<OrderDto> getOrdersByUserID(int userID) {
        List<Order> orderList = orderRepository.findByUserId(userID);

        return EntityDtoOrderMapper.toDto(orderList);
    }

    /**
     * Invokes Repository method to get Order with provided id.
     *
     * @param id is id of order to be returned.
     * @return {@link OrderDto} object with order data.
     * @throws OrderNotFoundException if no Order with provided id founded
     */
    @Override
    public OrderDto getOrder(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException(
                String.format(NO_ORDER_WITH_ID_FOUND, id),
                String.format(ERROR_CODE_ORDER_BY_ID_NOT_FOUND_FAILED, id),
                String.format(NOT_FOUND_BY_ID_PARAMETER, id)));

        return EntityDtoOrderMapper.toDto(order);
    }

    /**
     * Connects to database and add an new Order.
     *
     * @param createOrderParameter is {@link CreateOrderParameter} object with data provided
     * @return Created {@link OrderDto} object with Order data.
     * @throws UserNotFoundException                       if no User with provided userID founded
     * @throws GiftCertificateByParameterNotFoundException if GiftCertificate with provided giftID founded
     */
    @Transactional
    public OrderDto createOrder(CreateOrderParameter createOrderParameter) {
        if (!OrderValidator.validateCreateOrder(createOrderParameter)) {
            throw new DataValidationException(
                    DATA_VALIDATION_EXCEPTION, ERROR_CODE_ORDER_VALIDATION_FAILED);
        }

        Integer userID = createOrderParameter.getUser();
        Optional<User> userOptional = userRepository.findById(createOrderParameter.getUser());

        User user = userOptional.orElseThrow(() -> new UserNotFoundException(
                String.format(NO_USER_WITH_ID_FOUND, userID),
                String.format(ERROR_CODE_USER_BY_ID_NOT_FOUND_FAILED, userID),
                String.format(USER_NOT_FOUND_BY_ID_PARAMETER, userID)));

        List<GiftCertificate> giftCertificateList = new ArrayList<>();

        int orderPrice = 0;

        for (Integer giftID : createOrderParameter.getGifts()) {
            Optional<GiftCertificate> giftOptional = giftCertificateRepository.findById(giftID);

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

        Order resultOrder = orderRepository.save(order);
        return EntityDtoOrderMapper.toDto(resultOrder);
    }

    /**
     * Invokes Repository method to get List of all Orders from database.
     *
     * @param pageable is {@link Pageable} object with page number and page size
     * @return List of {@link OrderDto} objects with order data.
     */
    @Override
    public List<OrderDto> getOrders(Pageable pageable) {
        List<Order> orderList = orderRepository.findAll(pageable).toList();

        return EntityDtoOrderMapper.toDto(orderList);
    }

}
