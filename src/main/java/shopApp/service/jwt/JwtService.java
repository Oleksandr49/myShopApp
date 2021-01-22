package shopApp.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {

     String generateToken(UserDetails userDetails);
     <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
     String extractUsername(String token);
     Date extractExpiration(String token);
     Boolean validateToken(String token, UserDetails userDetails);
     Long getCustomerIdFromAuthHeader(String authHeader);

}
