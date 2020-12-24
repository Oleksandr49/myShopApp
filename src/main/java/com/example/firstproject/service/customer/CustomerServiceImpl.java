package com.example.firstproject.service.customer;

import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.repository.CustomerRepository;
import com.example.firstproject.service.customer.cart.CartService;
import com.example.firstproject.service.customer.details.DetailsService;
import com.example.firstproject.service.customer.order.OrderService;
import com.example.firstproject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            customer.setActive(true);
            customer.setRoles("ROLE_CUSTOMER");
            userService.create(customer);
    }

    @Override
    public Details readDetails(Long customerId) {
        return customerRepository.getOne(customerId).getDetails();
    }

    @Override
    public void updateDetails(Details newDetails, Long customerId) {
        Long detailsId = customerRepository.getOne(customerId).getDetails().getId();
        detailsService.updateDetails(newDetails, detailsId);
    }

    @Override
    public Address readAddress(Long customerId) {
        return customerRepository.getOne(customerId).getDetails().getAddress();
    }

    @Override
    public void updateAddress(Address newAddress, Long customerId) {
        Long detailsId = customerRepository.getOne(customerId).getDetails().getId();
        detailsService.updateAddress(newAddress, detailsId);
    }

    @Override
    public List<CustomerOrder> getOrderHistory(Long customerId) {
        return customerRepository.getOne(customerId).getDetails().getOrderHistory();

    }

    @Override
    public CustomerOrder readOrder(Long customerId, Long orderId) {
        return orderService.readOrder(customerRepository.getOne(customerId), orderId);
    }

    @Override
    public ShoppingCart readCart(Long customerId) {
        return customerRepository.getOne(customerId).getShoppingCart();
    }

    @Override
    public CustomerOrder CartToOrder(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        CustomerOrder customerOrder = orderService.assembleOrder(customer);
        cartService.clearCart(customer.getShoppingCart());
        return customerOrder;
    }

    @Override
    public void addItemToCart(Long customerId, Long productId) {
        Customer customer = customerRepository.getOne(customerId);
        cartService.addItemToCart(customer.getShoppingCart(), productId);
    }

    @Override
    public void removeItemFromCart(Long customerId, Long itemId) {
        Customer customer = customerRepository.getOne(customerId);
        cartService.removeItemFromCart(customer.getShoppingCart(), itemId);
    }

}
