package com.epam.esm.controller;

import com.epam.esm.controller.model.util.GiftCertificateModelAssembler;
import com.epam.esm.repository.model.util.FilteredGetGiftCertificateQueryParameter;
import com.epam.esm.repository.model.util.GetGiftCertificateQueryParameter;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.model.dto.GiftCertificateDTO;
import com.epam.esm.service.model.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final TagService tagService;
    private final GiftCertificateModelAssembler giftCertificateModelAssembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, TagService tagService,
                                     GiftCertificateModelAssembler giftCertificateModelAssembler) {

        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
        this.giftCertificateModelAssembler = giftCertificateModelAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<GiftCertificateDTO> getGiftCertificateByID(@PathVariable int id) {
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.getGiftCertificateByID(id);
        return giftCertificateModelAssembler.toModel(giftCertificateDTO);
    }

    @GetMapping("/{id}/tags")
    public List<TagDTO> getTagListByGiftCertificateID(@PathVariable int id) {
        return tagService.getTagListByGiftCertificateID(id);
    }

//    @GetMapping
//    public List<GiftCertificateDTO> getGiftCertificateByAllParams(GetGiftCertificateQueryParameter parameter) {
//        return giftCertificateService.getCertificates(parameter);
//    }

    @GetMapping
    public List<GiftCertificateDTO> getGiftCertificateByAllParams(FilteredGetGiftCertificateQueryParameter parameter) {
        return giftCertificateService.getCertificates(parameter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.createGiftCertificate(giftCertificateDTO);
    }

    @PutMapping("/{id}")
    public GiftCertificateDTO updateGiftCertificate(
            @RequestBody GiftCertificateDTO giftCertificateDTO, @PathVariable int id) {

        return giftCertificateService.updateCertificate(giftCertificateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        giftCertificateService.deleteCertificate(id);
    }

}
