package com.example.firstproject.model.product;

import com.example.firstproject.controller.products.ProductController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductEntityModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product product) {

        return EntityModel.of(product, //
                WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).readProductById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).addToCart("",product.getId())).withRel("addToCart"),
                linkTo(methodOn(ProductController.class).readAll()).withRel("products"));
    }
}



