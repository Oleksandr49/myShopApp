package com.example.firstproject.model.user;

import com.example.firstproject.controller.products.ProductController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserEntityModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {

        return EntityModel.of(user, //
                linkTo(methodOn(ProductController.class).readProductById(user.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).readAll()).withRel("customerOrders"));
    }
}
