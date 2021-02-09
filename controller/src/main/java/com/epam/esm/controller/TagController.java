package com.epam.esm.controller;

import com.epam.esm.repository.model.util.Page;
import com.epam.esm.service.TagService;
import com.epam.esm.service.model.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> getTags(@Valid Page page) {
        return tagService.getTags(page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto newTag(@RequestBody TagDto tagDTO) {
        return tagService.createTag(tagDTO);
    }

    @GetMapping("/{id}")
    public TagDto getGiftCertificateByID(@PathVariable int id) {
        return tagService.getTagByID(id);
    }

    @GetMapping(params = "name")
    public TagDto getTagByName(String name) {

        return tagService.getTagByName(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
    }

    @GetMapping("/getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders")
    public TagDto getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders() {
        return tagService.getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders();
    }
}
