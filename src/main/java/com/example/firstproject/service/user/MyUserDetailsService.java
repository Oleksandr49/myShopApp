package com.example.firstproject.service.user;

import com.example.firstproject.model.jwt.MyUserDetails;
import com.example.firstproject.model.user.User;
import com.example.firstproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUsersByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("Username " + s + "not found");
        }
        else {
            return new MyUserDetails(user);
        }
    }
}
