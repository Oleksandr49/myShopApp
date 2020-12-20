package com.example.firstproject.service.user;

import com.example.firstproject.model.user.User;

public interface UserService {

     void create (User user);
     User read (Long id);
     Boolean delete (Long id);
     User update (User user, Long id);
}
