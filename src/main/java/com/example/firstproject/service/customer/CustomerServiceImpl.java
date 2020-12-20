package com.example.firstproject.service.customer;

import com.example.firstproject.model.item.Item;
import com.example.firstproject.model.item.OrderItem;
import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.order.OrderPosition;
import com.example.firstproject.model.order.OrderState;
import com.example.firstproject.model.product.Product;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.model.user.customer.ShoppingCart;
import com.example.firstproject.repository.CustomerRepository;
import com.example.firstproject.repository.ItemRepository;
import com.example.firstproject.repository.OrderRepository;
import com.example.firstproject.repository.ProductRepository;
import com.example.firstproject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    UserService userService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void create(Customer customer) {
            customer.setActive(true);
            customer.setRoles("ROLE_CUSTOMER");
            userService.create(customer);
    }

    @Override
    public Details read(Long id) {
        Customer customer = customerRepository.getOne(id);
        return customer.getDetails();
    }

    @Override
    public Details update(Details newDetails, Long id) {
        Customer customer = customerRepository.getOne(id);
        Details details = customer.getDetails();
        details.setEmail(newDetails.getEmail());
        details.setFirstName(newDetails.getFirstName());
        details.setSecondName(newDetails.getSecondName());
        details.setMale(newDetails.getMale());
        customerRepository.save(customer);
        return details;
    }

    @Override
    public Boolean delete(Long id) {
        if(!customerRepository.existsById(id)){
            return false;
        }
        Customer customer = customerRepository.getOne(id);
        Details details = customer.getDetails();
        details.setEmail(null);
        details.setFirstName(null);
        details.setSecondName(null);
        details.setMale(null);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public Address createAddress(Address address, Long id) {
        Customer customer = customerRepository.getOne(id);
        Details details = customer.getDetails();
        details.setAddress(address);
        customerRepository.save(customer);
        return address;
    }

    @Override
    public Address readAddress(Long id) {
        Details details = customerRepository.getOne(id).getDetails();
        return details.getAddress();
    }

    @Override
    public Address updateAddress(Address newAddress, Long id) {
        Customer customer = customerRepository.getOne(id);
        Address address = customer.getDetails().getAddress();
        address.setCity(newAddress.getCity());
        address.setCountry(newAddress.getCountry());
        address.setPostalCode(newAddress.getPostalCode());
        address.setState(newAddress.getState());
        address.setStreet(newAddress.getStreet());
        customerRepository.save(customer);
        return address;
    }

    @Override
    public Boolean deleteAddress(Long id) {
        if(!customerRepository.existsById(id)){
            return false;
        }
        Customer customer = customerRepository.getOne(id);
        customer.getDetails().setAddress(null);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public ShoppingCart readShoppingCart(Long id) {
        return customerRepository.getOne(id).getShoppingCart();
    }

    @Override
    public CustomerOrder confirmCart(Long id) {
        Customer customer = customerRepository.getOne(id);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCreated(LocalDateTime.now());
        customerOrder.setTotalCost(shoppingCart.getTotalCost());
        customerOrder.setOrderState(OrderState.WAITING);
        customerOrder.setDetails(customer.getDetails());
        customer.getDetails().getOrderHistory().add(customerOrder);
        orderRepository.save(customerOrder);
        for(Item item : shoppingCart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setCustomerOrder(customerOrder);
            orderItem.setProductId(item.getProductId());
            orderItem.setAmount(item.getAmount());
            orderItem.setId(item.getId());
            customerOrder.getOrderItems().add(orderItem);
            itemRepository.deleteById(item.getId());
            itemRepository.save(orderItem);
        }
        shoppingCart.getCartItems().clear();
        shoppingCart.setTotalCost(0.00);
        customerRepository.save(customer);
        return customerOrder;
    }

    public OrderPosition createOrderPosition(Item item){
        Integer amount = item.getAmount();
        Product product = productRepository.getOne(item.getProductId());
        OrderPosition orderPosition = new OrderPosition(amount, product);
        orderPosition.setId(item.getId());
        return orderPosition;
    }

}
