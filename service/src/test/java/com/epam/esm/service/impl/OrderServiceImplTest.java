package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDAO;
import com.epam.esm.repository.dao.OrderDAO;
import com.epam.esm.repository.dao.UserDAO;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.exception.impl.GiftCertificateNotFoundException;
import com.epam.esm.service.exception.impl.OrderNotFoundException;
import com.epam.esm.service.exception.impl.UserNotFoundException;
import com.epam.esm.service.model.dto.OrderDTO;
import com.epam.esm.service.util.mapper.EntityDTOOrderMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final Integer TEST_ID = 1;
    private static final int TEST_USER_ID = 2;

    private static final int TEST_GIFT_ID = 3;
    private static final int TEST_PRICE = 500;
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderDAO orderDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;

    private Order testOrder;
    private OrderDTO testOrderDTO;
    private OrderDTO testOrderDTOWithoutID;
    private OrderDTO emptyOrderDTO;
    private User testUser;
    private GiftCertificate testGift;

    private List<Order> orderList;
    private List<OrderDTO> orderDTOList;

    @BeforeEach
    public void setUp() {
        emptyOrderDTO = new OrderDTO();

        testUser = new User();
        testUser.setId(TEST_USER_ID);

        testGift = new GiftCertificate();
        testGift.setId(TEST_GIFT_ID);

        testOrder = new Order();
        testOrder.setUser(testUser);
        testOrder.setGift(testGift);
        testOrder.setId(TEST_ID);
        testOrder.setPrice(TEST_PRICE);
        testOrder.setDate(TEST_DATE);

        testOrderDTOWithoutID = new OrderDTO();
        testOrderDTOWithoutID.setUserID(TEST_USER_ID);
        testOrderDTOWithoutID.setGiftID(TEST_GIFT_ID);
        testOrderDTOWithoutID.setId(TEST_ID);
        testOrderDTOWithoutID.setPrice(TEST_PRICE);
        testOrderDTOWithoutID.setDate(TEST_DATE);

        orderList = new ArrayList<>();
        orderList.add(testOrder);

        testOrderDTO = EntityDTOOrderMapper.toDTO(testOrder);

        orderDTOList = new ArrayList<>();
        orderDTOList.add(testOrderDTO);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getOrderByID() {
        given(orderDAO.getOrder(TEST_ID)).willReturn(Optional.of(testOrder));
        OrderDTO receivedOrderDTO = orderService.getOrder(TEST_ID);

        assertEquals(testOrderDTO, receivedOrderDTO);
    }

    @Test
    public void getOrderByIDShouldException() {
        given(orderDAO.getOrder(TEST_ID)).willReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(TEST_ID));
    }

    @Test
    public void createOrder() {
        given(orderDAO.createOrder(any())).willReturn(testOrder);
        given(userDAO.getUser(TEST_USER_ID)).willReturn(Optional.of(testUser));
        given(giftCertificateDAO.getGiftCertificateByID(TEST_GIFT_ID)).willReturn(Optional.of(testGift));

        OrderDTO receivedDTO = orderService.createOrder(TEST_USER_ID, TEST_GIFT_ID);

        assertEquals(TEST_ID, receivedDTO.getId());
        assertEquals(TEST_USER_ID, receivedDTO.getUserID());
        assertEquals(TEST_GIFT_ID, receivedDTO.getGiftID());
        assertEquals(TEST_DATE, receivedDTO.getDate());
        assertEquals(TEST_PRICE, receivedDTO.getPrice());
    }

    @Test
    public void createOrderShouldUserNotFoundException() {
        given(userDAO.getUser(anyInt())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> orderService.createOrder(TEST_USER_ID, TEST_GIFT_ID));
    }

    @Test
    public void createOrderShouldGiftNotFoundException() {
        given(userDAO.getUser(anyInt())).willReturn(Optional.of(testUser));
        given(giftCertificateDAO.getGiftCertificateByID(anyInt())).willReturn(Optional.empty());

        assertThrows(GiftCertificateNotFoundException.class,
                () -> orderService.createOrder(TEST_USER_ID, TEST_GIFT_ID));
    }

    @Test
    public void  getOrdersByUserID() {
        given(orderDAO.getOrdersByUserID(TEST_USER_ID)).willReturn(orderList);

        List<OrderDTO> receivedDTOList = orderService.getOrdersByUserID(TEST_USER_ID);
        assertIterableEquals(orderDTOList,receivedDTOList);
    }

}