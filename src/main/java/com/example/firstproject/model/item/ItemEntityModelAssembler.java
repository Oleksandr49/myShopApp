package com.example.firstproject.model.item;

import com.example.firstproject.controller.products.ProductController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemEntityModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {
    @Override
    public EntityModel<Item> toModel(Item item) {
        return EntityModel.of(item, //
                WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).removeFromCart(item.getId())).withSelfRel());

    }
}
