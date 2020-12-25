package com.example.firstproject.service.user;

import com.example.firstproject.model.user.User;
import com.example.firstproject.model.user.customer.Customer;

public interface UserService {

     void create (User user);
     void create (Customer customer);

     User read (Long id);
     Boolean delete (Long id);
     User update (User user, Long id);
}
