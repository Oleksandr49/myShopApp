package com.example.firstproject.service.userInfo;

import com.example.firstproject.model.users.UserInformation;

public interface UserInformationService {
    public void create (UserInformation userInformation, Integer userId);
    public UserInformation read (Integer id);
    public Boolean delete (Integer id);
    public void update (UserInformation user, Integer id);
}
