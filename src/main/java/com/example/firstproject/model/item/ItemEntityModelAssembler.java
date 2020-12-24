package com.example.firstproject.model.item;

import com.example.firstproject.controller.customer.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ItemEntityModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {
    @Override
    public EntityModel<Item> toModel(Item entity) {
        return EntityModel.of(entity, //
                WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).removeFromCart("", entity.getId())).withSelfRel());
                //link for amount changing
    }
}
