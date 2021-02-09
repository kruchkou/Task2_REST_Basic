package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.OrderDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.exception.impl.GiftCertificateByParameterNotFoundException;
import com.epam.esm.service.exception.impl.OrderNotFoundException;
import com.epam.esm.service.exception.impl.UserNotFoundException;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.model.dto.OrderDto;
import com.epam.esm.service.model.util.CreateOrderParameter;
import com.epam.esm.service.model.dto.UserInOrderDto;
import com.epam.esm.service.util.mapper.EntityDtoGiftCertificateMapper;
import com.epam.esm.service.util.mapper.EntityDtoOrderMapper;
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
    private OrderDao orderDao;
    @Mock
    private UserDao userDao;
    @Mock
    private GiftCertificateDao giftCertificateDao;

    private Order testOrder;
    private OrderDto testOrderDto;
    private CreateOrderParameter createOrderParameter;
    private CreateOrderParameter createOrderParameterWithNotExistUserID;
    private CreateOrderParameter createOrderParameterWithNotExistGiftID;
    private User testUser;
    private UserInOrderDto userDto;
    private GiftCertificate testGift;
    private List<GiftCertificate> giftCertificateList;
    private List<GiftCertificateDto> giftCertificateDtoList;
    private List<Order> orderList;
    private List<OrderDto> orderDtoList;

    @BeforeEach
    public void setUp() {
        List<Integer> giftIDList = new ArrayList<>();
        giftIDList.add(TEST_GIFT_ID);

        createOrderParameter = new CreateOrderParameter();
        createOrderParameter.setUser(TEST_USER_ID);
        createOrderParameter.setGifts(giftIDList);

        createOrderParameterWithNotExistUserID = new CreateOrderParameter();
        createOrderParameterWithNotExistUserID.setUser(NOT_EXIST_USER_ID);
        createOrderParameterWithNotExistUserID.setGifts(giftIDList);

        List<Integer> notExistGiftIDList = new ArrayList<>();
        notExistGiftIDList.add(NOT_EXIST_GIFT_ID);
        createOrderParameterWithNotExistGiftID = new CreateOrderParameter();
        createOrderParameterWithNotExistGiftID.setUser(TEST_USER_ID);
        createOrderParameterWithNotExistGiftID.setGifts(notExistGiftIDList);

        testUser = new User();
        testUser.setId(TEST_USER_ID);

        testGift = new GiftCertificate();
        testGift.setId(TEST_GIFT_ID);
        testGift.setPrice(TEST_PRICE);

        userDto = new UserInOrderDto();
        userDto.setId(TEST_USER_ID);

        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(testGift);
        giftCertificateDtoList = EntityDtoGiftCertificateMapper.toDto(giftCertificateList);

        testOrder = new Order();
        testOrder.setUser(testUser);
        testOrder.setGiftList(giftCertificateList);
        testOrder.setId(TEST_ID);
        testOrder.setPrice(TEST_PRICE);
        testOrder.setDate(TEST_DATE);

        orderList = new ArrayList<>();
        orderList.add(testOrder);

        testOrderDto = EntityDtoOrderMapper.toDto(testOrder);

        orderDtoList = new ArrayList<>();
        orderDtoList.add(testOrderDto);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getOrderByID() {
        given(orderDao.getOrder(TEST_ID)).willReturn(Optional.of(testOrder));
        OrderDto receivedOrderDto = orderService.getOrder(TEST_ID);

        assertEquals(testOrderDto, receivedOrderDto);
    }

    @Test
    public void getOrderByIDShouldException() {
        given(orderDao.getOrder(TEST_ID)).willReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(TEST_ID));
    }

    @Test
    public void createOrder() {
        given(orderDao.createOrder(any())).willReturn(testOrder);
        given(userDao.getUser(TEST_USER_ID)).willReturn(Optional.of(testUser));
        given(giftCertificateDao.getGiftCertificateByID(TEST_GIFT_ID)).willReturn(Optional.of(testGift));

        OrderDto receivedDto = orderService.createOrder(createOrderParameter);

        assertEquals(TEST_ID, receivedDto.getId());
        assertEquals(userDto, receivedDto.getUser());
        assertEquals(giftCertificateDtoList, receivedDto.getGifts());
        assertEquals(TEST_DATE, receivedDto.getDate());
        assertEquals(TEST_PRICE, receivedDto.getPrice());
    }

    @Test
    public void createOrderShouldUserNotFoundException() {
        given(userDao.getUser(anyInt())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> orderService.createOrder(createOrderParameterWithNotExistUserID));
    }

    @Test
    public void createOrderShouldGiftNotFoundException() {
        given(userDao.getUser(anyInt())).willReturn(Optional.of(testUser));
        given(giftCertificateDao.getGiftCertificateByID(anyInt())).willReturn(Optional.empty());

        assertThrows(GiftCertificateByParameterNotFoundException.class,
                () -> orderService.createOrder(createOrderParameterWithNotExistGiftID));
    }

    @Test
    public void  getOrdersByUserID() {
        given(orderDao.getOrdersByUserID(TEST_USER_ID)).willReturn(orderList);

        List<OrderDto> receivedDtoList = orderService.getOrdersByUserID(TEST_USER_ID);
        assertIterableEquals(orderDtoList,receivedDtoList);
    }

}