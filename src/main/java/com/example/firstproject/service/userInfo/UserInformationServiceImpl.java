package com.example.firstproject.service.userInfo;

import com.example.firstproject.model.users.UserInformation;
import com.example.firstproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationServiceImpl implements UserInformationService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void create(UserInformation userInformation, Integer userId) {
        userRepository.findById(userId)
                .map(user -> {
                    userInformation.setUserInfoId(user.getId());
                    user.setUserInformation(userInformation);
                    return userRepository.save(user);
                })
                .orElseThrow();
    }

    @Override
    public UserInformation read(Integer id) {
       return userRepository.getOne(id).getUserInformation();
    }

    @Override
    public Boolean delete(Integer id) {
        userRepository.findById(id)
                .map(user -> {
                    user.getUserInformation().setName(null);
                    user.getUserInformation().setSurname(null);
                    user.getUserInformation().setUserAddress(null);
                    user.getUserInformation().setEmail(null);
                    userRepository.save(user);
                    return true;
                })
                .orElseThrow();
        return false;
    }

    @Override
    public void update(UserInformation newUserInformation, Integer id) {
        userRepository.findById(id)
                .map(user -> {
                    user.setUserInformation(newUserInformation);
                    return userRepository.save(user);
                })
                .orElseThrow();
    }
}
