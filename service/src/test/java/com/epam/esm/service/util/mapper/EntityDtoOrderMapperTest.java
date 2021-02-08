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
    private OrderDto testOrderDTO;

    private List<GiftCertificate> giftCertificateList;
    private List<GiftCertificateDto> giftCertificateDTOList;

    @BeforeEach
    public void init() {
        testOrder = new Order();
        testOrderDTO = new OrderDto();

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

        giftCertificateDTOList = EntityDtoGiftCertificateMapper.toDTO(giftCertificateList);

        testOrderDTO.setId(TEST_ID);
        testOrderDTO.setGifts(giftCertificateDTOList);
        testOrderDTO.setPrice(TEST_PRICE);
        testOrderDTO.setDate(TEST_DATE);
    }

    @Test
    public void shouldConvertToEntity() {
        Order order = EntityDtoOrderMapper.toEntity(testOrderDTO);

        assertEquals(TEST_ID, order.getId());
        assertEquals(TEST_PRICE, order.getPrice());
        assertEquals(TEST_DATE, order.getDate());
    }

    @Test
    public void shouldConvertToDTO() {
        OrderDto orderDTO = EntityDtoOrderMapper.toDTO(testOrder);

        assertEquals(TEST_ID, orderDTO.getId());
        assertEquals(giftCertificateDTOList, orderDTO.getGifts());
        assertEquals(TEST_PRICE, orderDTO.getPrice());
        assertEquals(TEST_DATE, orderDTO.getDate());
    }
}