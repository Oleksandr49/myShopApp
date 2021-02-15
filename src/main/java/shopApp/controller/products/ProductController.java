package shopApp.controller.products;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.product.Product;
import shopApp.service.product.ProductService;
import shopApp.service.product.ProductWrapper;

import javax.validation.Valid;

/**
 * <p>Works with Product entity by using ProductService and ProductWrapper instances.</p>
 * <p>Depending on authentication and request method allows CRUD operations (Requirements in methods description).</p>
 * <p>Requests should have application/json media type, response type will be application/hal+json</p>
 * @see ProductService
 * @see ProductWrapper
 */

@RestController
@RequiredArgsConstructor
public class ProductController {

    final private ProductService productService;
    final private ProductWrapper productWrapper;

    /**
     * <p>Responds with list of available in database products wrapped into CollectionModel.
     * Does not require previous authentication.</p>
     * @return {@link CollectionModel} object with list of all available products and corresponding links.
     * <p>Example of response JSON template:</p>
     *<pre>{@code
     * {
     *      "_embedded": {
     *          "productList": [
     *              {
     *                  "id": LongValue,
     *                  "productName": "StringValue",
     *                  "productPrice": IntegerValue,
     *                  "_links": {
     *                      "self: {
     *                          "href": "StringValue"
     *                      }
     *                  }
     *               }, ...
     *          ]
     *      },
     *      "_links": {
     *          "self": {
     *              "href": "StringValue"
     *          }
     *      }
     * }
     *}</pre>
     * @see Product
     */
    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> readAll() {
        return productWrapper.toEntityCollection(productService.readAllProducts());
    }

    /**
     * Responds with specified by ID Product entity.
     * Does not require previous authentication.
     * @param productId, provided as PathVariable.
     * @return {@link EntityModel} object with included chosen Product entity and corresponding links.
     * <p>Example of response JSON template:</p>
     * <pre>{@code
     * {
     *      "id": LongValue,
     *      "productName": "StringValue",
     *      "productPrice": IntegerValue,
     *      "_links": {
     *          "self: {
     *              "href": "StringValue"
     *          }
     *      }
     * }
     * }</pre>
     * @see Product
     */
    @GetMapping("/products/{productId}")
    public EntityModel<Product> readProductById(@PathVariable Long productId) {
        return productWrapper.toModel(productService.read(productId));
    }

    /**
     * Creates a Product entity in database. Responds with created Product entity.
     * Usage requires previous authentication with ADMIN role.
     * @param product valid Product entity JSON.
     *                Example:<pre>{@code
     * {
     *      "productName": "NotBlankStringValue",
     *      "productPrice": IntegerValueGreaterThanZero
     * }
     * }</pre>
     * @return <p>{@link EntityModel} object with included created Product entity and corresponding links.</p>
     * Return JSON template same as in "readProductById()".
     * @see Product
     */
    @PostMapping("/products")
    public EntityModel<Product> create(@Valid @RequestBody Product product) {
        return productWrapper.toModel(productService.create(product));
    }

    /**
     * <p>Updates a Product entity in database by replacing all existing field for indicated Product with new values.</p>
     * Responds with updated Product entity.
     * Usage requires previous authentication with ADMIN role.
     * @param product valid Product entity, same as Example from "create()" description.
     * @param id of Product entity to be updated
     * @return {@link EntityModel} serialized object with included updated Product entity and corresponding links.
     * REturn JSOM template same as in "create()"
     * @see Product
     */
    @PutMapping("/products/{id}")
    public EntityModel<Product> update(@Valid @RequestBody Product product, @PathVariable Long id){
        return productWrapper.toModel(productService.update(product, id));
    }

    /**
     * Removes a Product entity from database if Product with given ID exists.
     * Usage requires previous authentication with ADMIN role.
     * @param id of Product entity to be removed.
     */
    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id) {
         productService.delete(id);
    }
}
