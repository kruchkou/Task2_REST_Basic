package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class EntityDTOOrderMapper {
    
    private EntityDTOOrderMapper() {
    }

    /**
     * Transforms DTO to Entity
     *
     * @param orderDTO is {@link OrderDTO} object with data to transform
     * @return transformed to {@link Order} entity.
     */
    public static Order toEntity(OrderDTO orderDTO) {
        Order order = new Order();

        order.setId(orderDTO.getId());
        order.setPrice(orderDTO.getPrice());
        order.setDate(orderDTO.getDate());

        return order;
    }

    /**
     * Transforms Entity to DTO
     *
     * @param order is {@link Order} object with data to transform
     * @return transformed to {@link OrderDTO} data.
     */
    public static OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());
        orderDTO.setUserID(order.getUser().getId());
        orderDTO.setGiftID(order.getGift().getId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setDate(order.getDate());

        return orderDTO;
    }

    /**
     * Transforms List of Entities to List of DTOs
     *
     * @param orderList is List of {@link Order} object with data to transform
     * @return transformed to List of {@link OrderDTO} data.
     */
    public static List<OrderDTO> toDTO(List<Order> orderList) {
        List<OrderDTO> orderDTOList = new ArrayList<>();

        orderList.forEach(tag -> orderDTOList.add(toDTO(tag)));

        return orderDTOList;
    }

    /**
     * Transforms List of DTOs to List of Entities
     *
     * @param orderDTOList is List of {@link OrderDTO} object with data to transform
     * @return transformed to List of {@link Order} data.
     */
    public static List<Order> toEntity(List<OrderDTO> orderDTOList) {
        List<Order> orderList = new ArrayList<>();

        orderDTOList.forEach(tagDTO -> orderList.add(toEntity(tagDTO)));

        return orderList;
    }
    
}
