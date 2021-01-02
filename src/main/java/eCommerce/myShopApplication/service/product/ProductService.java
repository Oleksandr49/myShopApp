package eCommerce.myShopApplication.service.product;

import eCommerce.myShopApplication.model.product.Product;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface ProductService extends RepresentationModelAssembler<Product, EntityModel<Product>> {

     CollectionModel<EntityModel<Product>> readAllProducts();
     EntityModel<Product> create (Product product);
     EntityModel<Product> read (Long id);
     EntityModel<Product> update (Product product, Long id);
     Product getProduct(Long id);
     Boolean delete (Long id);
     Boolean validate(Product product);
}
