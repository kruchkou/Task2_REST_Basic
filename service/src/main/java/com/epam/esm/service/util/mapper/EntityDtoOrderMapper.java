package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.model.dto.OrderDto;
import com.epam.esm.service.model.util.UserInOrder;

import java.util.ArrayList;
import java.util.List;

public class EntityDtoOrderMapper {
    
    private EntityDtoOrderMapper() {
    }

    /**
     * Transforms DTO to Entity
     *
     * @param orderDTO is {@link OrderDto} object with data to transform
     * @return transformed to {@link Order} entity.
     */
    public static Order toEntity(OrderDto orderDTO) {
        Order order = new Order();

        order.setId(orderDTO.getId());
        order.setPrice(orderDTO.getPrice());
        order.setDate(orderDTO.getDate());

        List<GiftCertificate> giftCertificateList = EntityDtoGiftCertificateMapper.toEntity(orderDTO.getGifts());
        order.setGiftList(giftCertificateList);

        return order;
    }

    /**
     * Transforms Entity to DTO
     *
     * @param order is {@link Order} object with data to transform
     * @return transformed to {@link OrderDto} data.
     */
    public static OrderDto toDTO(Order order) {
        OrderDto orderDTO = new OrderDto();

        orderDTO.setId(order.getId());

        UserInOrder userInOrder = toUserInOrder(order.getUser());
        orderDTO.setUser(userInOrder);

        List<GiftCertificateDto> giftCertificateDTOList = EntityDtoGiftCertificateMapper.toDTO(order.getGiftList());
        orderDTO.setGifts(giftCertificateDTOList);

        orderDTO.setPrice(order.getPrice());
        orderDTO.setDate(order.getDate());

        return orderDTO;
    }

    private static UserInOrder toUserInOrder(User user) {
        UserInOrder userInOrder = new UserInOrder();

        userInOrder.setId(user.getId());
        userInOrder.setName(user.getName());

        return userInOrder;
    }

    /**
     * Transforms List of Entities to List of DTOs
     *
     * @param orderList is List of {@link Order} object with data to transform
     * @return transformed to List of {@link OrderDto} data.
     */
    public static List<OrderDto> toDTO(List<Order> orderList) {
        List<OrderDto> orderDTOList = new ArrayList<>();

        orderList.forEach(tag -> orderDTOList.add(toDTO(tag)));

        return orderDTOList;
    }

    /**
     * Transforms List of DTOs to List of Entities
     *
     * @param orderDTOList is List of {@link OrderDto} object with data to transform
     * @return transformed to List of {@link Order} data.
     */
    public static List<Order> toEntity(List<OrderDto> orderDTOList) {
        List<Order> orderList = new ArrayList<>();

        orderDTOList.forEach(tagDTO -> orderList.add(toEntity(tagDTO)));

        return orderList;
    }
    
}
