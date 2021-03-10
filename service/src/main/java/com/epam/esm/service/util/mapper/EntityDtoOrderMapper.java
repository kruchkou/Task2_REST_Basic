package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.model.dto.OrderDto;
import com.epam.esm.service.model.dto.UserInOrderDto;

import java.util.ArrayList;
import java.util.List;

public final class EntityDtoOrderMapper {
    
    private EntityDtoOrderMapper() {
    }

    /**
     * Transforms Dto to Entity
     *
     * @param orderDto is {@link OrderDto} object with data to transform
     * @return transformed to {@link Order} entity.
     */
    public static Order toEntity(OrderDto orderDto) {
        Order order = new Order();

        order.setId(orderDto.getId());
        order.setPrice(orderDto.getPrice());
        order.setDate(orderDto.getDate());

        List<GiftCertificate> giftCertificateList = EntityDtoGiftCertificateMapper.toEntity(orderDto.getGifts());
        order.setGiftList(giftCertificateList);

        return order;
    }

    /**
     * Transforms Entity to Dto
     *
     * @param order is {@link Order} object with data to transform
     * @return transformed to {@link OrderDto} data.
     */
    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());

        UserInOrderDto userInOrder = toUserInOrder(order.getUser());
        orderDto.setUser(userInOrder);

        List<GiftCertificateDto> giftCertificateDtoList = EntityDtoGiftCertificateMapper.toDto(order.getGiftList());
        orderDto.setGifts(giftCertificateDtoList);

        orderDto.setPrice(order.getPrice());
        orderDto.setDate(order.getDate());

        return orderDto;
    }

    private static UserInOrderDto toUserInOrder(User user) {
        UserInOrderDto userInOrder = new UserInOrderDto();

        userInOrder.setId(user.getId());
        userInOrder.setName(user.getName());

        return userInOrder;
    }

    /**
     * Transforms List of Entities to List of Dtos
     *
     * @param orderList is List of {@link Order} object with data to transform
     * @return transformed to List of {@link OrderDto} data.
     */
    public static List<OrderDto> toDto(List<Order> orderList) {
        List<OrderDto> orderDtoList = new ArrayList<>();

        orderList.forEach(tag -> orderDtoList.add(toDto(tag)));

        return orderDtoList;
    }

    /**
     * Transforms List of Dtos to List of Entities
     *
     * @param orderDtoList is List of {@link OrderDto} object with data to transform
     * @return transformed to List of {@link Order} data.
     */
    public static List<Order> toEntity(List<OrderDto> orderDtoList) {
        List<Order> orderList = new ArrayList<>();

        orderDtoList.forEach(tagDto -> orderList.add(toEntity(tagDto)));

        return orderList;
    }
    
}
