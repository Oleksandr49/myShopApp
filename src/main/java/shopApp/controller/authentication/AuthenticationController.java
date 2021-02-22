package shopApp.controller.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shopApp.model.jwt.AuthenticationRequest;
import shopApp.model.jwt.AuthenticationResponse;
import shopApp.service.jwt.JwtService;
import shopApp.service.user.MyUserDetailsService;

import javax.validation.Valid;

/**
 * Uses MyUserDetailsService, AuthenticationManager and JwtService instances
 * for user authentication with JWT.
 * @see MyUserDetailsService
 * @see AuthenticationManager
 * @see JwtService
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    final private MyUserDetailsService myUserDetailsService;
    final private AuthenticationManager authenticationManager;
    final private JwtService jwtService;

    /**
     * Authenticates user by username/password pair.
     * In case of correct request generates JWT for user and responds with AuthenticationResponse.
     * When pair is incorrect throw exception and return "BadRequest" status.
     * @param authenticationRequest {@link AuthenticationRequest} containing valid username and password.
     * @return {@link AuthenticationResponse} with token value in "jwt" field.
     * @throws BadCredentialsException in case of invalid or incorrect username/password pair.
     */
    @PostMapping("/authentication")
    public AuthenticationResponse createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwt);
    }
}
