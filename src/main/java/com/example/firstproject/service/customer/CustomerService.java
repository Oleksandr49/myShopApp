package com.example.firstproject.service.customer;

import com.example.firstproject.model.item.Item;
import com.example.firstproject.model.order.CustomerOrder;
import com.example.firstproject.model.order.OrderPosition;
import com.example.firstproject.model.user.customer.Address;
import com.example.firstproject.model.user.customer.Customer;
import com.example.firstproject.model.user.customer.Details;
import com.example.firstproject.model.user.customer.ShoppingCart;

public interface CustomerService {

    void create (Customer customer);
    Details read (Long id);
    Details update (Details details, Long id);
    Boolean delete(Long id);
    Address createAddress(Address address, Long id);
    Address readAddress (Long id);
    Address updateAddress (Address address, Long id);
    Boolean deleteAddress (Long id);
    ShoppingCart readShoppingCart (Long id);
    CustomerOrder confirmCart(Long id);
    OrderPosition createOrderPosition(Item item);
}
