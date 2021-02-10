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

//Description
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    final private MyUserDetailsService myUserDetailsService;
    final private AuthenticationManager authenticationManager;
    final private JwtService jwtService;

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
