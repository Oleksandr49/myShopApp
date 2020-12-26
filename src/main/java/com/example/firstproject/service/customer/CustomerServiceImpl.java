package com.example.firstproject.service.customer;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Cart;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.repository.CustomerRepository;
import com.example.firstproject.service.customer.cart.CartService;
import com.example.firstproject.service.customer.details.DetailsService;
import com.example.firstproject.service.customer.order.OrderService;
import com.example.firstproject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserService userService;
    @Autowired
    DetailsService detailsService;
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;

    @Override
    public void create(Customer customer) {
            userService.create(customer);
    }

    @Override
    public EntityModel<Details> readDetails(Long customerId) {
        Details details = customerRepository.getOne(customerId).getDetails();
        return detailsService.toModel(details);
    }

    @Override
    public EntityModel<Details> updateDetails(Details newDetails, Long customerId) {
        Long detailsId = customerRepository.getOne(customerId).getDetails().getId();
        return detailsService.updateDetails(newDetails, detailsId);
    }

    @Override
    public Address readAddress(Long customerId) {
        Address address = customerRepository.getOne(customerId).getDetails().getAddress();
        if(address == null){
            return new Address();
        }
        return address;
    }

    @Override
    public Address updateAddress(Address newAddress, Long customerId) {
        Long detailsId = customerRepository.getOne(customerId).getDetails().getId();
        return detailsService.updateAddress(newAddress, detailsId);
    }

    @Override
    public CollectionModel<EntityModel<CustomerOrder>> getOrderHistory(Long customerId) {
        return orderService.getOrderHistory(customerRepository.getOne(customerId));
    }

    @Override
    public EntityModel<CustomerOrder> readOrder(Long customerId, Long orderId) {
        return orderService.readOrder(customerRepository.getOne(customerId), orderId);
    }

    @Override
    public EntityModel<Cart> readCart(Long customerId) {
        return cartService.toModel(customerRepository.getOne(customerId).getShoppingCart());
    }

    @Override
    public EntityModel<CustomerOrder> CartToOrder(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        EntityModel<CustomerOrder> customerOrder = orderService.assembleOrder(customer);
        cartService.emptyCart(customer.getShoppingCart());
        return customerOrder;
    }

    @Override
    public EntityModel<Cart> emptyCart(Long id) {
        Cart cart = customerRepository.getOne(id).getShoppingCart();
        cartService.emptyCart(cart);
        return cartService.toModel(cart);
    }

    @Override
    public EntityModel<Cart> addItemToCart(Long customerId, Long productId) {
        Cart cart = customerRepository.getOne(customerId).getShoppingCart();
        cartService.addItemToCart(cart, productId);
        return cartService.toModel(cart);
    }

    @Override
    public EntityModel<Cart> removeItemFromCart(Long customerId, Long itemId) {
        Cart cart = customerRepository.getOne(customerId).getShoppingCart();
        cartService.removeItemFromCart(cart, itemId);
        return cartService.toModel(cart);
    }

}
