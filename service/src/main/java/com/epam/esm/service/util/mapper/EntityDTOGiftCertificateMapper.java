package com.epam.esm.service.util.mapper;

import com.epam.esm.repository.model.entity.GiftCertificate;
import com.epam.esm.repository.model.entity.Tag;
import com.epam.esm.service.model.dto.GiftCertificateDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is Mapper that links GiftCertificates Entities with DTOs.
 */
public final class EntityDTOGiftCertificateMapper {

    private EntityDTOGiftCertificateMapper() {
    }

    /**
     * Transforms DTO to Entity
     *
     * @param giftCertificateDTO is {@link GiftCertificateDTO} object with data to transform
     * @return transformed to {@link GiftCertificate} entity.
     */
    public static GiftCertificate toEntity(GiftCertificateDTO giftCertificateDTO) {
        LocalDateTime createDateLocalDateTime = giftCertificateDTO.getCreateDate();
        LocalDateTime lastUpdateDateLocalDateTime = giftCertificateDTO.getLastUpdateDate();

        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(giftCertificateDTO.getId());
        giftCertificate.setName(giftCertificateDTO.getName());
        giftCertificate.setDescription(giftCertificateDTO.getDescription());
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificate.setDuration(giftCertificateDTO.getDuration());

        if (createDateLocalDateTime != null) {
            Instant createDateInstant = giftCertificateDTO.getCreateDate().toInstant(ZoneOffset.UTC);
            giftCertificate.setCreateDate(createDateInstant);
        }

        if (lastUpdateDateLocalDateTime != null) {
            Instant lastUpdateDateZonedDateInstant = giftCertificateDTO.getLastUpdateDate().toInstant(ZoneOffset.UTC);
            giftCertificate.setLastUpdateDate(lastUpdateDateZonedDateInstant);
        }

        return giftCertificate;
    }

    /**
     * Transforms Entity to DTO
     *
     * @param giftCertificate is {@link GiftCertificate} object with data to transform
     * @return transformed to {@link GiftCertificateDTO} data.
     */
    public static GiftCertificateDTO toDTO(GiftCertificate giftCertificate) {
        Instant createDateLocalInstant = giftCertificate.getCreateDate();
        Instant lastUpdateDateInstant = giftCertificate.getLastUpdateDate();
        List<Tag> tagList = giftCertificate.getTagList();

        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();

        giftCertificateDTO.setId(giftCertificate.getId());
        giftCertificateDTO.setName(giftCertificate.getName());
        giftCertificateDTO.setDescription(giftCertificate.getDescription());
        giftCertificateDTO.setPrice(giftCertificate.getPrice());
        giftCertificateDTO.setDuration(giftCertificate.getDuration());

        if (tagList != null) {
            List<String> tagNamesList = new ArrayList<>();

            tagList.forEach(tag -> tagNamesList.add(tag.getName()));
            giftCertificateDTO.setTagNames(tagNamesList);
        }

        if (createDateLocalInstant != null) {
            LocalDateTime createDateLocalDateTime = LocalDateTime.ofInstant(giftCertificate.getCreateDate(), ZoneOffset.UTC);
            giftCertificateDTO.setCreateDate(createDateLocalDateTime);
        }

        if (lastUpdateDateInstant != null) {
            LocalDateTime lastUpdateDateLocalDateTime = LocalDateTime.ofInstant(giftCertificate.getLastUpdateDate(), ZoneOffset.UTC);
            giftCertificateDTO.setLastUpdateDate(lastUpdateDateLocalDateTime);
        }

        return giftCertificateDTO;
    }

    /**
     * Transforms List of Entities to List of DTOs
     *
     * @param giftCertificateList is List of {@link GiftCertificate} object with data to transform
     * @return transformed to List of {@link GiftCertificateDTO} data.
     */
    public static List<GiftCertificateDTO> toDTO(List<GiftCertificate> giftCertificateList) {
        List<GiftCertificateDTO> giftCertificateDTOList = new ArrayList<>();

        giftCertificateList.forEach(giftCertificate -> {
            GiftCertificateDTO giftCertificateDTO = toDTO(giftCertificate);
            giftCertificateDTOList.add(giftCertificateDTO);
        });

        return giftCertificateDTOList;
    }

    /**
     * Transforms List of DTOs to List of Entities
     *
     * @param giftCertificateDTOList is List of {@link GiftCertificateDTO} object with data to transform
     * @return transformed to List of {@link GiftCertificate} data.
     */
    public static List<GiftCertificate> toEntity(List<GiftCertificateDTO> giftCertificateDTOList) {
        List<GiftCertificate> giftCertificateList = new ArrayList<>();

        giftCertificateDTOList.forEach(giftCertificateDTO -> {
            GiftCertificate giftCertificate = toEntity(giftCertificateDTO);
            giftCertificateList.add(giftCertificate);
        });

        return giftCertificateList;
    }

}
