package com.example.firstproject.controllers;

import com.example.firstproject.model.users.User;
import com.example.firstproject.model.users.UserAddress;
import com.example.firstproject.model.users.UserInformation;
import com.example.firstproject.service.jwt.JwtService;
import com.example.firstproject.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class UsersController {

    @Autowired
    JwtService jwtService;

    @Autowired
    RegistrationService registrationService;

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> readUserById(@PathVariable Integer id) {
        final User user = registrationService.read(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users/{id}")
    public User update(@RequestBody User user, @PathVariable Integer id){
        return registrationService.update(user, id);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        return registrationService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
