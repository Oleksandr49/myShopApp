package shopApp.service.customer.cart;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import shopApp.controller.customer.CartController;
import shopApp.controller.customer.CustomerController;
import shopApp.model.user.customer.Cart;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CartWrapper implements RepresentationModelAssembler<Cart, EntityModel<Cart>> {

    @Override
    public EntityModel<Cart> toModel(Cart entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(CartController.class).readCart("")).withSelfRel(),
                linkTo(methodOn(CustomerController.class).confirmCart("")).withRel("OrderCart"));
    }
}
