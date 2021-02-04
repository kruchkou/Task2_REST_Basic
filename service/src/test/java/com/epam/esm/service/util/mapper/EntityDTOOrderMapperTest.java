package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDTOOrderMapperTest {

    private static final Integer TEST_ID = 1;
    private static final int TEST_USER_ID = 2;
    private static final int TEST_GIFT_ID = 3;
    private static final int TEST_PRICE = 500;
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();

    private Order testOrder;
    private OrderDTO testOrderDTO;

    @BeforeEach
    public void init() {
        testOrder = new Order();
        testOrderDTO = new OrderDTO();

        User testUser = new User();
        GiftCertificate testGift = new GiftCertificate();

        testUser.setId(TEST_USER_ID);
        testGift.setId(TEST_GIFT_ID);

        testOrder.setUser(testUser);
        testOrder.setGift(testGift);

        testOrder.setId(TEST_ID);
        testOrder.setPrice(TEST_PRICE);
        testOrder.setDate(TEST_DATE);

        testOrderDTO.setId(TEST_ID);
        testOrderDTO.setUserID(TEST_USER_ID);
        testOrderDTO.setGiftID(TEST_GIFT_ID);
        testOrderDTO.setPrice(TEST_PRICE);
        testOrderDTO.setDate(TEST_DATE);
    }

    @Test
    public void shouldConvertToEntity() {
        Order order = EntityDTOOrderMapper.toEntity(testOrderDTO);

        assertEquals(TEST_ID, order.getId());
        assertEquals(TEST_PRICE, order.getPrice());
        assertEquals(TEST_DATE, order.getDate());
    }

    @Test
    public void shouldConvertToDTO() {
        OrderDTO orderDTO = EntityDTOOrderMapper.toDTO(testOrder);

        assertEquals(TEST_ID, orderDTO.getId());
        assertEquals(TEST_USER_ID, orderDTO.getUserID());
        assertEquals(TEST_GIFT_ID, orderDTO.getGiftID());
        assertEquals(TEST_PRICE, orderDTO.getPrice());
        assertEquals(TEST_DATE, orderDTO.getDate());
    }
}