package com.example.firstproject.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

    @Autowired
    JwtService jwtService;

    public Integer getIdFromToken(HttpServletRequest request){
        String token = extractTokenFromRequest(request);
        String id = jwtService.extractClaim(token, Claims::getId);
        return Integer.parseInt(id);
    }

    private String extractTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        return header.substring(7);
    }
}
