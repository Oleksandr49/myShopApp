package shopApp.service.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import shopApp.repository.UserRepository;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final byte[] SECRET_KEY = getEncryptedSecret();
    private static final String secret = "Some_very_long_message_for_better_security";

    final private UserRepository userRepository;

     @Override
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        String userID = String.valueOf(userRepository.findUsersByUsername(userDetails.getUsername()).getId());
        return createToken(claims, userDetails.getUsername(), userID);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public Long getCustomerIdFromAuthHeader(String authHeader){
        String id = this.extractClaim(authHeader.substring(7), Claims::getId);
        return Long.parseLong(id);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private String createToken(Map<String, Object> claims, String subject, String userID){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .setId(userID)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private byte[] getEncryptedSecret(){
        String encryptedSecret = DatatypeConverter.printBase64Binary(secret.getBytes());
        return DatatypeConverter.parseBase64Binary(encryptedSecret);
    }





}
