package com.example.firstproject.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

@Component
public class JwtUtil {

    private static final String secret = "Some_very_long_message_for_better_security";

    @Autowired
    JwtService jwtService;

    public Long getIdFromAuthHeader(String authHeader){
        String id = jwtService.extractClaim(authHeader.substring(7), Claims::getId);
        return Long.parseLong(id);
    }

    public static byte[] getEncryptedSecret(){
        String encryptedSecret = DatatypeConverter.printBase64Binary(secret.getBytes());
        return DatatypeConverter.parseBase64Binary(encryptedSecret);
    }
}
