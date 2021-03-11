package com.epam.esm.controller.util.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateModelAssembler implements RepresentationModelAssembler<GiftCertificateDto,
        EntityModel<GiftCertificateDto>> {

    @Override
    public EntityModel<GiftCertificateDto> toModel(GiftCertificateDto giftCertificateDto) {
        return EntityModel.of(giftCertificateDto,
                linkTo(methodOn(GiftCertificateController.class).getGiftCertificateByID(
                        giftCertificateDto.getId())).withSelfRel(),
                linkTo(methodOn(GiftCertificateController.class).getGiftCertificateByAllParams(
                        new GetGiftCertificateQueryParameter(), Pageable.unpaged())).withRel("Gift certificates"),
                linkTo(methodOn(GiftCertificateController.class).getTagListByGiftCertificateID(
                        giftCertificateDto.getId())).withRel("Tags"));
    }

    public List<EntityModel<GiftCertificateDto>> toModel(List<GiftCertificateDto> giftCertificateDto) {
        return giftCertificateDto.stream().map(this::toModel).collect(Collectors.toList());
    }

}
