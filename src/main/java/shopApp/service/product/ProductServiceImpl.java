package shopApp.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import shopApp.controller.customer.CustomerController;
import shopApp.controller.products.ProductController;
import shopApp.model.product.Product;
import shopApp.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CollectionModel<EntityModel<Product>>  readAllProducts() {
        List<Product> products = productRepository.findAll();
        return toEntityCollection(products);
    }

    @Override
    public EntityModel<Product> create(Product product) {
        productRepository.save(product);
        return toModel(product);
    }

    @Override
    public EntityModel<Product> read(Long id) {
        return toModel(getProduct(id));
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.getOne(id);
    }

    @Override
    public EntityModel<Product> update (Product newProduct, Long id){
        return productRepository.findById(id)
       .map(product -> {
           product.setProductName(newProduct.getProductName());
           product.setProductPrice(newProduct.getProductPrice());
           return toModel(productRepository.save(product));
       })
       .orElseGet(()->{
           newProduct.setId(id);
           return toModel(productRepository.save(newProduct));
       });
    }

    @Override
    public Boolean delete(Long id) {
        if(!productRepository.existsById(id)){
           return false;
        }
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public EntityModel<Product> toModel(Product entity) {
        return EntityModel.of(entity, //
                WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).readProductById(entity.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(CustomerController.class).addToCart("",entity.getId())).withRel("addToCart"));
    }

    private CollectionModel<EntityModel<Product>> toEntityCollection(List <Product> products){
        List<EntityModel<Product>> productsEntityModels = products.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(productsEntityModels, linkTo(methodOn(ProductController.class).readAll()).withSelfRel());
    }
}
