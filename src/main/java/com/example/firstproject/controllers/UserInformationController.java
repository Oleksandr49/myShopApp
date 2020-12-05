package com.example.firstproject.controllers;

import com.example.firstproject.model.users.UserInformation;
import com.example.firstproject.service.jwt.JwtUtil;
import com.example.firstproject.service.userInfo.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserInformationController {
    @Autowired
    UserInformationService userInformationService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(value = "/UserInformation")
    public ResponseEntity<?> create(HttpServletRequest httpServletRequest, @RequestBody UserInformation userInformation) {
        userInformationService.create(userInformation, jwtUtil.getIdFromToken(httpServletRequest));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/UserInformation")
    public ResponseEntity<UserInformation> readUserInformationById(HttpServletRequest httpServletRequest) {
        final UserInformation userInformation = userInformationService.read(jwtUtil.getIdFromToken(httpServletRequest));

        return userInformation != null
                ? new ResponseEntity<>(userInformation, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/UserInformation")
    public void update(@RequestBody UserInformation userInformation, HttpServletRequest httpServletRequest){
         userInformationService.update(userInformation, jwtUtil.getIdFromToken(httpServletRequest));
    }

    @DeleteMapping(value = "/UserInformation")
    public ResponseEntity<?> delete(HttpServletRequest httpServletRequest) {

        return userInformationService.delete(jwtUtil.getIdFromToken(httpServletRequest))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
