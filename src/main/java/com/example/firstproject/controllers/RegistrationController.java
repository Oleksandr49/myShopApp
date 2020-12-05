package com.example.firstproject.controllers;

import com.example.firstproject.model.users.User;
import com.example.firstproject.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping(value = "/Registration")
    public ResponseEntity<?> create(@RequestBody User user) {
        registrationService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
