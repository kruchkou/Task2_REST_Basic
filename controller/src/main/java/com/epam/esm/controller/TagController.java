package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.model.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> getTags() {
        return tagService.getTags();
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
    public TagDto getTagsByName(@RequestParam(value = "name") String name) {

        return tagService.getTagByName(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        tagService.deleteTag(id);
    }

}
