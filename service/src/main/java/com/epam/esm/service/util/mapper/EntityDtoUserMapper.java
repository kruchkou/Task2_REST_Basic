package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.Order;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.model.dto.UserDto;
import com.epam.esm.service.model.util.UserOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class is Mapper that links Tag Entities with DTOs.
 */
public final class EntityDtoUserMapper {

    private EntityDtoUserMapper() {
    }

    /**
     * Transforms DTO to Entity
     *
     * @param userDTO is {@link UserDto} object with data to transform
     * @return transformed to {@link User} entity.
     */
    public static User toEntity(UserDto userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());

        return user;
    }

    /**
     * Transforms Entity to DTO
     *
     * @param user is {@link User} object with data to transform
     * @return transformed to {@link UserDto} data.
     */
    public static UserDto toDTO(User user) {
        UserDto userDTO = new UserDto();

        List<UserOrder> orderDTOList = toUserOrder(user.getOrderList());

        userDTO.setOrderList(orderDTOList);

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());

        return userDTO;
    }

    private static List<UserOrder> toUserOrder(List<Order> orders) {
        if(orders != null) {
            return orders.stream().map(EntityDtoUserMapper::toUserOrder).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private static UserOrder toUserOrder(Order order) {
        UserOrder userOrder = new UserOrder();

        userOrder.setId(order.getId());

        List<GiftCertificateDto> giftCertificateDTOList = EntityDtoGiftCertificateMapper.toDTO(order.getGiftList());
        userOrder.setGifts(giftCertificateDTOList);

        userOrder.setPrice(order.getPrice());
        userOrder.setDate(order.getDate());

        return userOrder;
    }

    /**
     * Transforms List of Entities to List of DTOs
     *
     * @param userList is List of {@link User} object with data to transform
     * @return transformed to List of {@link UserDto} data.
     */
    public static List<UserDto> toDTO(List<User> userList) {
        List<UserDto> userDTOList = new ArrayList<>();

        userList.forEach(user -> userDTOList.add(toDTO(user)));

        return userDTOList;
    }

    /**
     * Transforms List of DTOs to List of Entities
     *
     * @param userDTOList is List of {@link UserDto} object with data to transform
     * @return transformed to List of {@link User} data.
     */
    public static List<User> toEntity(List<UserDto> userDTOList) {
        List<User> userList = new ArrayList<>();

        userDTOList.forEach(tagDTO -> userList.add(toEntity(tagDTO)));

        return userList;
    }
}
