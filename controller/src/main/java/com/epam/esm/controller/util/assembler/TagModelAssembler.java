package com.epam.esm.controller.util.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.service.model.dto.TagDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<TagDto,
        EntityModel<TagDto>> {

    @Override
    public EntityModel<TagDto> toModel(TagDto tagDto) {
        return EntityModel.of(tagDto,
                linkTo(methodOn(TagController.class).getTagByID(tagDto.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).getTags(Pageable.unpaged())).withRel("Tags"));
    }

    public List<EntityModel<TagDto>> toModel(List<TagDto> tagDto) {
        return tagDto.stream().map(this::toModel).collect(Collectors.toList());
    }

}
