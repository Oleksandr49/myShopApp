package com.example.firstproject.service.customer;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.repository.CustomerRepository;
import com.example.firstproject.service.customer.cart.CartService;
import com.example.firstproject.service.customer.details.DetailsService;
import com.example.firstproject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void create(Customer customer) {
            customer.setActive(true);
            customer.setRoles("ROLE_CUSTOMER");
            userService.create(customer);
    }

    @Override
    public Details readDetails(Long customerId) {
        return detailsService.readDetails(customerRepository.getOne(customerId));
    }

    @Override
    public void updateDetails(Details newDetails, Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        detailsService.updateDetails(newDetails, customer);
        customerRepository.save(customer);
    }

    @Override
    public void deleteDetails(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        detailsService.deleteDetails(customer);
        customerRepository.save(customer);
    }

    @Override
    public void createAddress(Address address, Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        detailsService.createAddress(address, customer);
        customerRepository.save(customer);
    }

    @Override
    public Address readAddress(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        return detailsService.readAddress(customer);
    }

    @Override
    public void updateAddress(Address newAddress, Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        detailsService.updateAddress(newAddress, customer);
        customerRepository.save(customer);
    }

    @Override
    public void deleteAddress(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        detailsService.deleteAddress(customer);
        customerRepository.save(customer);
    }

    @Override
    public ShoppingCart readCart(Long customerId) {
        return cartService.readShoppingCart(customerRepository.getOne(customerId));
    }

    @Override
    public void clearCart(Long customerId) {
        cartService.clearCart(customerRepository.getOne(customerId));
    }

    @Override
    public CustomerOrder orderCart(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        CustomerOrder customerOrder = cartService.cartToOrder(customer);
        customerRepository.save(customer);
        return customerOrder;
    }

    @Override
    public void addItemToCart(Long customerId, Long productId) {
        Customer customer = customerRepository.getOne(customerId);
        cartService.addItemToCart(customer, productId);
    }
}
