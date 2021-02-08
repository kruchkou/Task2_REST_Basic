package com.epam.esm.controller.model.util;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.model.dto.GiftCertificateDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateModelAssembler implements RepresentationModelAssembler<GiftCertificateDTO,
        EntityModel<GiftCertificateDTO>> {

        @Override
        public EntityModel<GiftCertificateDTO> toModel(GiftCertificateDTO giftCertificateDTO) {
            return EntityModel.of(giftCertificateDTO,
                    linkTo(methodOn(GiftCertificateController.class).getGiftCertificateByID(
                            giftCertificateDTO.getId())).withSelfRel(),
                    linkTo(methodOn(GiftCertificateController.class).getGiftCertificateByAllParams(
                            new GetGiftCertificateQueryParameter())).withRel("Gift certificates"),
                    linkTo(methodOn(GiftCertificateController.class).getTagListByGiftCertificateID(
                            giftCertificateDTO.getId())).withRel("Tags"));
        }

}
