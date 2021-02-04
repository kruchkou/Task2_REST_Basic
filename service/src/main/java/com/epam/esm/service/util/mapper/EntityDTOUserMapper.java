package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.repository.model.entity.User;
import com.epam.esm.service.model.dto.TagDTO;
import com.epam.esm.service.model.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is Mapper that links Tag Entities with DTOs.
 */
public final class EntityDTOUserMapper {

    private EntityDTOUserMapper() {
    }

    /**
     * Transforms DTO to Entity
     *
     * @param userDTO is {@link UserDTO} object with data to transform
     * @return transformed to {@link User} entity.
     */
    public static User toEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());

        return user;
    }

    /**
     * Transforms Entity to DTO
     *
     * @param user is {@link User} object with data to transform
     * @return transformed to {@link UserDTO} data.
     */
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());

        return userDTO;
    }

    /**
     * Transforms List of Entities to List of DTOs
     *
     * @param userList is List of {@link User} object with data to transform
     * @return transformed to List of {@link UserDTO} data.
     */
    public static List<UserDTO> toDTO(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();

        userList.forEach(tag -> userDTOList.add(toDTO(tag)));

        return userDTOList;
    }

    /**
     * Transforms List of DTOs to List of Entities
     *
     * @param userDTOList is List of {@link UserDTO} object with data to transform
     * @return transformed to List of {@link User} data.
     */
    public static List<User> toEntity(List<UserDTO> userDTOList) {
        List<User> userList = new ArrayList<>();

        userDTOList.forEach(tagDTO -> userList.add(toEntity(tagDTO)));

        return userList;
    }
}
