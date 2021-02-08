package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.model.dto.GiftCertificateDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is Mapper that links GiftCertificates Entities with DTOs.
 */
public final class EntityDtoGiftCertificateMapper {

    private EntityDtoGiftCertificateMapper() {
    }

    /**
     * Transforms DTO to Entity
     *
     * @param giftCertificateDTO is {@link GiftCertificateDto} object with data to transform
     * @return transformed to {@link GiftCertificate} entity.
     */
    public static GiftCertificate toEntity(GiftCertificateDto giftCertificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(giftCertificateDTO.getId());
        giftCertificate.setName(giftCertificateDTO.getName());
        giftCertificate.setDescription(giftCertificateDTO.getDescription());
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificate.setDuration(giftCertificateDTO.getDuration());
        giftCertificate.setCreateDate(giftCertificateDTO.getCreateDate());
        giftCertificate.setLastUpdateDate(giftCertificateDTO.getLastUpdateDate());

        return giftCertificate;
    }

    /**
     * Transforms Entity to DTO
     *
     * @param giftCertificate is {@link GiftCertificate} object with data to transform
     * @return transformed to {@link GiftCertificateDto} data.
     */
    public static GiftCertificateDto toDTO(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDTO = new GiftCertificateDto();

        giftCertificateDTO.setId(giftCertificate.getId());
        giftCertificateDTO.setName(giftCertificate.getName());
        giftCertificateDTO.setDescription(giftCertificate.getDescription());
        giftCertificateDTO.setPrice(giftCertificate.getPrice());
        giftCertificateDTO.setDuration(giftCertificate.getDuration());
        giftCertificateDTO.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDTO.setLastUpdateDate(giftCertificate.getLastUpdateDate());

        List<Tag> tagList = giftCertificate.getTagList();
        if (tagList != null) {
            List<String> tagNamesList = new ArrayList<>();

            tagList.forEach(tag -> tagNamesList.add(tag.getName()));
            giftCertificateDTO.setTags(tagNamesList);
        }

        return giftCertificateDTO;
    }

    /**
     * Transforms List of Entities to List of DTOs
     *
     * @param giftCertificateList is List of {@link GiftCertificate} object with data to transform
     * @return transformed to List of {@link GiftCertificateDto} data.
     */
    public static List<GiftCertificateDto> toDTO(List<GiftCertificate> giftCertificateList) {
        List<GiftCertificateDto> giftCertificateDTOList = new ArrayList<>();

        giftCertificateList.forEach(giftCertificate -> {
            GiftCertificateDto giftCertificateDTO = toDTO(giftCertificate);
            giftCertificateDTOList.add(giftCertificateDTO);
        });

        return giftCertificateDTOList;
    }

    /**
     * Transforms List of DTOs to List of Entities
     *
     * @param giftCertificateDTOList is List of {@link GiftCertificateDto} object with data to transform
     * @return transformed to List of {@link GiftCertificate} data.
     */
    public static List<GiftCertificate> toEntity(List<GiftCertificateDto> giftCertificateDTOList) {
        List<GiftCertificate> giftCertificateList = new ArrayList<>();

        giftCertificateDTOList.forEach(giftCertificateDTO -> {
            GiftCertificate giftCertificate = toEntity(giftCertificateDTO);
            giftCertificateList.add(giftCertificate);
        });

        return giftCertificateList;
    }

}
