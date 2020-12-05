package com.example.firstproject;

import com.example.firstproject.config.SecurityConfig;
import com.example.firstproject.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class FirstProjectApplication {

    public static void main(String[] args){
        SpringApplication.run(FirstProjectApplication.class, args);
    }
}
