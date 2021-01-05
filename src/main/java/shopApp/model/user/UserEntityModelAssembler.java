package shopApp.model.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import shopApp.controller.products.ProductController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserEntityModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {

        return EntityModel.of(user, //
                WebMvcLinkBuilder.linkTo(methodOn(ProductController.class).readProductById(user.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).readAll()).withRel("customerOrders"));
    }
}
