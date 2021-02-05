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
import com.epam.esm.service.model.dto.GiftCertificateDTO;
import com.epam.esm.service.model.dto.OrderDTO;
import com.epam.esm.service.model.dto.UserDTO;
import com.epam.esm.service.model.util.CreateOrderParameter;
import com.epam.esm.service.util.mapper.EntityDTOGiftCertificateMapper;
import com.epam.esm.service.util.mapper.EntityDTOOrderMapper;
import com.epam.esm.service.util.mapper.EntityDTOUserMapper;
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
    private static final int NOT_EXIST_USER_ID = 15;
    private static final int NOT_EXIST_GIFT_ID = 15;

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
    private CreateOrderParameter createOrderParameter;
    private CreateOrderParameter createOrderParameterWithNotExistUserID;
    private CreateOrderParameter createOrderParameterWithNotExistGiftID;
    private OrderDTO testOrderDTOWithoutID;
    private User testUser;
    private UserDTO userDTO;
    private GiftCertificate testGift;
    private List<GiftCertificate> giftCertificateList;
    private List<GiftCertificateDTO> giftCertificateDTOList;
    private List<Order> orderList;
    private List<OrderDTO> orderDTOList;

    @BeforeEach
    public void setUp() {
        List<Integer> giftIDList = new ArrayList<>();
        giftIDList.add(TEST_GIFT_ID);

        createOrderParameter = new CreateOrderParameter();
        createOrderParameter.setUserID(TEST_USER_ID);
        createOrderParameter.setGiftIDList(giftIDList);

        createOrderParameterWithNotExistUserID = new CreateOrderParameter();
        createOrderParameterWithNotExistUserID.setUserID(NOT_EXIST_USER_ID);
        createOrderParameterWithNotExistUserID.setGiftIDList(giftIDList);

        List<Integer> notExistGiftIDList = new ArrayList<>();
        notExistGiftIDList.add(NOT_EXIST_GIFT_ID);
        createOrderParameterWithNotExistGiftID = new CreateOrderParameter();
        createOrderParameterWithNotExistGiftID.setUserID(TEST_USER_ID);
        createOrderParameterWithNotExistGiftID.setGiftIDList(notExistGiftIDList);

        testUser = new User();
        testUser.setId(TEST_USER_ID);

        testGift = new GiftCertificate();
        testGift.setId(TEST_GIFT_ID);
        testGift.setPrice(TEST_PRICE);

        userDTO = EntityDTOUserMapper.toDTO(testUser);

        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(testGift);
        giftCertificateDTOList = EntityDTOGiftCertificateMapper.toDTO(giftCertificateList);

        testOrder = new Order();
        testOrder.setUser(testUser);
        testOrder.setGiftList(giftCertificateList);
        testOrder.setId(TEST_ID);
        testOrder.setPrice(TEST_PRICE);
        testOrder.setDate(TEST_DATE);

        testOrderDTOWithoutID = new OrderDTO();
        testOrderDTOWithoutID.setUser(userDTO);
        testOrderDTOWithoutID.setGiftList(giftCertificateDTOList);
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

        OrderDTO receivedDTO = orderService.createOrder(createOrderParameter);

        assertEquals(TEST_ID, receivedDTO.getId());
        assertEquals(userDTO, receivedDTO.getUser());
        assertEquals(giftCertificateDTOList, receivedDTO.getGiftList());
        assertEquals(TEST_DATE, receivedDTO.getDate());
        assertEquals(TEST_PRICE, receivedDTO.getPrice());
    }

    @Test
    public void createOrderShouldUserNotFoundException() {
        given(userDAO.getUser(anyInt())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> orderService.createOrder(createOrderParameterWithNotExistUserID));
    }

    @Test
    public void createOrderShouldGiftNotFoundException() {
        given(userDAO.getUser(anyInt())).willReturn(Optional.of(testUser));
        given(giftCertificateDAO.getGiftCertificateByID(anyInt())).willReturn(Optional.empty());

        assertThrows(GiftCertificateNotFoundException.class,
                () -> orderService.createOrder(createOrderParameterWithNotExistGiftID));
    }

    @Test
    public void  getOrdersByUserID() {
        given(orderDAO.getOrdersByUserID(TEST_USER_ID)).willReturn(orderList);

        List<OrderDTO> receivedDTOList = orderService.getOrdersByUserID(TEST_USER_ID);
        assertIterableEquals(orderDTOList,receivedDTOList);
    }

}