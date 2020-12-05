package com.example.firstproject.service.registration;

import com.example.firstproject.model.users.User;

public interface RegistrationService {

    public void create (User user);
    public User read (Integer id);
    public Boolean delete (Integer id);
    public User update (User user, Integer id);
}
