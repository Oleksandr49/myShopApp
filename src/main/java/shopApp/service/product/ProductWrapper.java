package shopApp.service.product;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import shopApp.controller.customer.CustomerController;
import shopApp.controller.products.ProductController;
import shopApp.model.product.Product;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ProductWrapper implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product entity) {
        return EntityModel.of(entity, //
                WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).readProductById(entity.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).addToCart("",entity.getId())).withRel("addToCart"));
    }

    public CollectionModel<EntityModel<Product>> toEntityCollection(List<Product> products){
        List<EntityModel<Product>> productsEntityModels = products.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(productsEntityModels, linkTo(methodOn(ProductController.class).readAll()).withSelfRel());
    }
}
