package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.GiftCertificateModelAssembler;
import com.epam.esm.controller.util.assembler.TagModelAssembler;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.epam.esm.service.model.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("v1/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final TagService tagService;
    private final GiftCertificateModelAssembler giftCertificateModelAssembler;
    private final TagModelAssembler tagModelAssembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, TagService tagService,
                                     GiftCertificateModelAssembler giftCertificateModelAssembler,
                                     TagModelAssembler tagModelAssembler) {

        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
        this.giftCertificateModelAssembler = giftCertificateModelAssembler;
        this.tagModelAssembler = tagModelAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<GiftCertificateDto> getGiftCertificateByID(@PathVariable int id) {
        return giftCertificateModelAssembler.toModel(giftCertificateService.getGiftCertificateByID(id));
    }

    @GetMapping("/{id}/tags")
    public List<EntityModel<TagDto>> getTagListByGiftCertificateID(@PathVariable int id) {
        return tagModelAssembler.toModel(tagService.getTagListByGiftCertificateID(id));
    }

    @GetMapping
    public List<EntityModel<GiftCertificateDto>> getGiftCertificateByAllParams(
            @Valid GetGiftCertificateQueryParameter parameter) {

        return giftCertificateModelAssembler.toModel(giftCertificateService.getCertificates(parameter));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<GiftCertificateDto> createGiftCertificate(
            @Valid @RequestBody GiftCertificateDto giftCertificateDto) {

        return giftCertificateModelAssembler.toModel(giftCertificateService.createGiftCertificate(giftCertificateDto));
    }

    @PutMapping("/{id}")
    public EntityModel<GiftCertificateDto> updateGiftCertificate(
            @Valid @RequestBody GiftCertificateDto giftCertificateDto, @PathVariable int id) {

        return giftCertificateModelAssembler.toModel(giftCertificateService.updateCertificate(giftCertificateDto, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        giftCertificateService.deleteCertificate(id);
    }

}
