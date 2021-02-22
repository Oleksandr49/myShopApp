package shopApp.service.product;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import shopApp.controller.products.ProductController;
import shopApp.model.product.Product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Wraps Product entity so it will be connected with all necessary links to improve navigation.
 * Includes also {@link #toCollectionModel(Iterable)} from implemented interface.
 * @see RepresentationModelAssembler
 * @see Product
 */
@Component
public class ProductWrapper implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    /**
     * @param productEntity to be wrapped with EntityModel.
     * @return {@link EntityModel} with Product entity and corresponding links included.
     */
    @Override
    public EntityModel<Product> toModel(Product productEntity) {
        return EntityModel.of(productEntity, //
                WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).readProductById(productEntity.getId())).withSelfRel());
    }
}
