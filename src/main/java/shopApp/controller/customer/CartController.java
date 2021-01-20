package shopApp.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import shopApp.model.user.customer.Cart;
import shopApp.service.customer.cart.CartService;
import shopApp.service.customer.cart.CartWrapper;
import shopApp.service.jwt.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers/carts")
public class CartController {

    private final String headerName = "Authorization";

    final private CartService cartService;
    final private JwtService jwtService;
    final private CartWrapper cartWrapper;

    @GetMapping
    public EntityModel<Cart> readCart(@RequestHeader(name = headerName)String header) {
        return cartWrapper.toModel(cartService.readCart(jwtService.getCustomerIdFromAuthHeader(header)));
    }

    @DeleteMapping
    public void emptyCart(@RequestHeader(name = headerName)String header) {
        cartService.emptyCart(jwtService.getCustomerIdFromAuthHeader(header));
    }

    @PutMapping
    public void addToCart(@RequestHeader(name = headerName)String header, @PathVariable Long productId) {
       cartService.addItemToCart(jwtService.getCustomerIdFromAuthHeader(header), productId);
    }

    @DeleteMapping
    public void removeFromCart(@RequestHeader(name = headerName)String header, @PathVariable Long itemId) {
         cartService.removeItemFromCart(jwtService.getCustomerIdFromAuthHeader(header), itemId);
    }




}