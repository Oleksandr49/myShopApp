package com.example.firstproject.model.order;

import com.example.firstproject.controller.products.ProductController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderPositionEntityModelAssembler implements RepresentationModelAssembler<OrderPosition, EntityModel<OrderPosition>> {
    @Override
    public EntityModel<OrderPosition> toModel(OrderPosition orderPosition) {
        return EntityModel.of(orderPosition,
                linkTo(methodOn(ProductController.class).removeFromCart("", orderPosition.getId())).withRel("removeFromCart"));
    }
}
