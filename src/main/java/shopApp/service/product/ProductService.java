package shopApp.service.product;

import org.springframework.stereotype.Service;
import shopApp.model.product.Product;

import javax.validation.Valid;
import java.util.List;

/**
 * CRUD operations methods for Product entity.
 * Used by ProductController.
 * @see Product
 * @see shopApp.controller.products.ProductController
 */
@Service
public interface ProductService {

     /**
      * Gets list of all available in database Products
      * @return List of {@link Product} entities.
      */
     List<Product> readAllProducts();

     /**
      * Creates new Product entity.
      * @param product Valid Product entity to be added to database.
      * @return {@link Product} entity that was created.
      */
     Product create (@Valid Product product);

     /**
      * Gets specified by ID value Product entity from database.
      * @param ID of Product entity to be returned
      * @return {@link Product} entity
      */
     Product read (Long ID);

     /**
      * Updates a Product entity in database by replacing all existing field for indicated Product with new values.
      * Responds with updated Product entity.
      * @param newProduct Valid Product entity, which will replace existing Product with specified ID.
      * @param ID of Product which will be updated.
      * @return updated {@link Product} entity
      */
     Product update (@Valid Product newProduct, Long ID);

     /**
      * Removes specified by ID Product entity from database.
      * @param ID of the Product to be removed.
      */
     void delete (Long ID);

}
