package com.example.firstproject.model.order;

import com.example.firstproject.controller.customer.CustomerController;
import com.example.firstproject.controller.paypal.PayPallController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class OrderEntityModelAssembler implements RepresentationModelAssembler<CustomerOrder, EntityModel<CustomerOrder>> {

    @Override
    public EntityModel<CustomerOrder> toModel(CustomerOrder entity) {
        return EntityModel.of(entity, //
                WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).readOrder("", entity.getId())).withSelfRel(),
                linkTo(methodOn(PayPallController.class).makePayment(entity)).withRel("addToCart"));
        //Link for canceling the order
    }
}
