package shopApp.service.customer.cart;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import shopApp.controller.customer.CartController;
import shopApp.controller.customer.OrderController;
import shopApp.model.user.customer.Cart;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartWrapper implements RepresentationModelAssembler<Cart, EntityModel<Cart>> {

    @Override
    public EntityModel<Cart> toModel(Cart entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(CartController.class).readCart("")).withSelfRel(),
                linkTo(methodOn(OrderController.class).orderCart("")).withRel("OrderCart"));
    }
}
