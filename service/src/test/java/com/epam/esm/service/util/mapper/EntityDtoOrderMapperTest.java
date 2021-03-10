package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.model.dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDtoOrderMapperTest {

    private static final Integer TEST_ID = 1;
    private static final int TEST_USER_ID = 2;
    private static final int TEST_GIFT_ID = 3;
    private static final int TEST_PRICE = 500;
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();

    private Order testOrder;
    private OrderDto testOrderDto;

    private List<GiftCertificate> giftCertificateList;
    private List<GiftCertificateDto> giftCertificateDtoList;

    @BeforeEach
    public void init() {
        testOrder = new Order();
        testOrderDto = new OrderDto();

        User testUser = new User();
        testUser.setId(TEST_USER_ID);

        GiftCertificate testGift = new GiftCertificate();
        testGift.setId(TEST_GIFT_ID);

        testOrder.setUser(testUser);
        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(testGift);
        testOrder.setGiftList(giftCertificateList);

        testOrder.setId(TEST_ID);
        testOrder.setPrice(TEST_PRICE);
        testOrder.setDate(TEST_DATE);

        giftCertificateDtoList = EntityDtoGiftCertificateMapper.toDto(giftCertificateList);

        testOrderDto.setId(TEST_ID);
        testOrderDto.setGifts(giftCertificateDtoList);
        testOrderDto.setPrice(TEST_PRICE);
        testOrderDto.setDate(TEST_DATE);
    }

    @Test
    public void shouldConvertToEntity() {
        Order order = EntityDtoOrderMapper.toEntity(testOrderDto);

        assertEquals(TEST_ID, order.getId());
        assertEquals(TEST_PRICE, order.getPrice());
        assertEquals(TEST_DATE, order.getDate());
    }

    @Test
    public void shouldConvertToDto() {
        OrderDto orderDto = EntityDtoOrderMapper.toDto(testOrder);

        assertEquals(TEST_ID, orderDto.getId());
        assertEquals(giftCertificateDtoList, orderDto.getGifts());
        assertEquals(TEST_PRICE, orderDto.getPrice());
        assertEquals(TEST_DATE, orderDto.getDate());
    }
}