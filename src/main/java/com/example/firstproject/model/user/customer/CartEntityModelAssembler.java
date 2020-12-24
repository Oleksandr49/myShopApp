package com.example.firstproject.model.user.customer;

import com.example.firstproject.controller.customer.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CartEntityModelAssembler implements RepresentationModelAssembler {
    @Override
    public RepresentationModel<?> toModel(Object entity) {
        return EntityModel.of(entity, //
                WebMvcLinkBuilder.
                linkTo(methodOn(CustomerController.class).readShoppingCart("")).withSelfRel());
    }
}
