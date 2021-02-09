package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.TagModelAssembler;
import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.TagService;
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
@RequestMapping("v1/tags")
public class TagController {

    private final TagService tagService;
    private final TagModelAssembler tagModelAssembler;

    @Autowired
    public TagController(TagService tagService, TagModelAssembler tagModelAssembler) {
        this.tagService = tagService;
        this.tagModelAssembler = tagModelAssembler;
    }

    @GetMapping
    public List<EntityModel<TagDto>> getTags(@Valid Page page) {
        return tagModelAssembler.toModel(tagService.getTags(page));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TagDto> newTag(@RequestBody TagDto tagDto) {
        return tagModelAssembler.toModel(tagService.createTag(tagDto));
    }

    @GetMapping("/{id}")
    public EntityModel<TagDto> getTagByID(@PathVariable int id) {
        return tagModelAssembler.toModel(tagService.getTagByID(id));
    }

    @GetMapping(params = "name")
    public EntityModel<TagDto> getTagByName(String name) {

        return tagModelAssembler.toModel(tagService.getTagByName(name));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
    }

    @GetMapping("/getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders")
    public EntityModel<TagDto> getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders() {
        return tagModelAssembler.toModel(tagService.getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders());
    }
}
