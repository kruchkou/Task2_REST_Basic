package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.model.dto.GiftCertificateDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Class is Mapper that links GiftCertificates Entities with Dtos.
 */
public final class EntityDtoGiftCertificateMapper {

    private EntityDtoGiftCertificateMapper() {
    }

    /**
     * Transforms Dto to Entity
     *
     * @param giftCertificateDto is {@link GiftCertificateDto} object with data to transform
     * @return transformed to {@link GiftCertificate} entity.
     */
    public static GiftCertificate toEntity(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(giftCertificateDto.getId());
        giftCertificate.setName(giftCertificateDto.getName());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        giftCertificate.setCreateDate(giftCertificateDto.getCreateDate());
        giftCertificate.setLastUpdateDate(giftCertificateDto.getLastUpdateDate());

        return giftCertificate;
    }

    /**
     * Transforms Entity to Dto
     *
     * @param giftCertificate is {@link GiftCertificate} object with data to transform
     * @return transformed to {@link GiftCertificateDto} data.
     */
    public static GiftCertificateDto toDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();

        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());

        List<Tag> tagList = giftCertificate.getTagList();
        if (tagList != null) {
            List<String> tagNamesList = new ArrayList<>();

            tagList.forEach(tag -> tagNamesList.add(tag.getName()));
            giftCertificateDto.setTags(tagNamesList);
        }

        return giftCertificateDto;
    }

    /**
     * Transforms List of Entities to List of Dtos
     *
     * @param giftCertificateList is List of {@link GiftCertificate} object with data to transform
     * @return transformed to List of {@link GiftCertificateDto} data.
     */
    public static List<GiftCertificateDto> toDto(List<GiftCertificate> giftCertificateList) {
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();

        giftCertificateList.forEach(giftCertificate -> {
            GiftCertificateDto giftCertificateDto = toDto(giftCertificate);
            giftCertificateDtoList.add(giftCertificateDto);
        });

        return giftCertificateDtoList;
    }

    /**
     * Transforms List of Dtos to List of Entities
     *
     * @param giftCertificateDtoList is List of {@link GiftCertificateDto} object with data to transform
     * @return transformed to List of {@link GiftCertificate} data.
     */
    public static List<GiftCertificate> toEntity(List<GiftCertificateDto> giftCertificateDtoList) {
        List<GiftCertificate> giftCertificateList = new ArrayList<>();

        giftCertificateDtoList.forEach(giftCertificateDto -> {
            GiftCertificate giftCertificate = toEntity(giftCertificateDto);
            giftCertificateList.add(giftCertificate);
        });

        return giftCertificateList;
    }

}
