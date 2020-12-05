package com.example.firstproject.service.order;

import com.example.firstproject.model.orders.CustomerOrder;
import com.example.firstproject.model.orders.OrderItem;
import com.example.firstproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderUtil orderUtil;

    @Override
    public List<CustomerOrder> readAllProducts(Integer id) {
        return userRepository.getOne(id).getCustomerOrderHistory();
    }

    @Override
    public CustomerOrder create(List<OrderItem> orderItems, Integer id) {
    CustomerOrder order = orderUtil.assembleOrder(orderItems);
    userRepository.findById(id)
            .map(user -> {
                user.getCustomerOrderHistory().add(order);
                userRepository.save(user);
                return order;
            }).orElseThrow();
    return order;
    }

    @Override
    public CustomerOrder read(Integer id) {
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        userRepository.findById(id)
                .map(user -> {
                    user.getCustomerOrderHistory().clear();
                    System.out.println(user.getCustomerOrderHistory());
                    userRepository.save(user);
                    return true;
                })
                .orElseThrow();
        return false;
    }

    @Override
    public CustomerOrder update(CustomerOrder product, Integer id) {
        return null;
    }
}
