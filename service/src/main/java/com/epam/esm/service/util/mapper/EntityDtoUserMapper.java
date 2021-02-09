package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.model.dto.UserDto;
import com.epam.esm.service.model.dto.UserOrderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class is Mapper that links Tag Entities with Dtos.
 */
public final class EntityDtoUserMapper {

    private EntityDtoUserMapper() {
    }

    /**
     * Transforms Dto to Entity
     *
     * @param userDto is {@link UserDto} object with data to transform
     * @return transformed to {@link User} entity.
     */
    public static User toEntity(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());

        return user;
    }

    /**
     * Transforms Entity to Dto
     *
     * @param user is {@link User} object with data to transform
     * @return transformed to {@link UserDto} data.
     */
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        List<UserOrderDto> orderDtoList = toUserOrder(user.getOrderList());

        userDto.setOrderList(orderDtoList);

        userDto.setId(user.getId());
        userDto.setName(user.getName());

        return userDto;
    }

    private static List<UserOrderDto> toUserOrder(List<Order> orders) {
        if(orders != null) {
            return orders.stream().map(EntityDtoUserMapper::toUserOrder).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static UserOrderDto toUserOrder(Order order) {
        UserOrderDto userOrder = new UserOrderDto();

        userOrder.setId(order.getId());

        List<GiftCertificateDto> giftCertificateDtoList = EntityDtoGiftCertificateMapper.toDto(order.getGiftList());
        userOrder.setGifts(giftCertificateDtoList);

        userOrder.setPrice(order.getPrice());
        userOrder.setDate(order.getDate());

        return userOrder;
    }

    /**
     * Transforms List of Entities to List of Dtos
     *
     * @param userList is List of {@link User} object with data to transform
     * @return transformed to List of {@link UserDto} data.
     */
    public static List<UserDto> toDto(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();

        userList.forEach(user -> userDtoList.add(toDto(user)));

        return userDtoList;
    }

    /**
     * Transforms List of Dtos to List of Entities
     *
     * @param userDtoList is List of {@link UserDto} object with data to transform
     * @return transformed to List of {@link User} data.
     */
    public static List<User> toEntity(List<UserDto> userDtoList) {
        List<User> userList = new ArrayList<>();

        userDtoList.forEach(tagDto -> userList.add(toEntity(tagDto)));

        return userList;
    }
}
