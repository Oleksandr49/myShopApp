package com.example.firstproject.model.user.customer;

import com.example.firstproject.controller.customer.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DetailsEntityModelAssembler implements RepresentationModelAssembler<Details, EntityModel<Details>> {
    @Override
    public EntityModel<Details> toModel(Details details) {
        return EntityModel.of(details,
                linkTo(methodOn(CustomerController.class).read("")).withSelfRel(),
                linkTo(methodOn(CustomerController.class).readAddress("")).withRel("Address"));
    }
}
