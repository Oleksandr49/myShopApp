package shopApp.config;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import shopApp.service.jwt.JwtService;
import shopApp.service.user.MyUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    final private HandlerExceptionResolver exceptionResolver;

    final private MyUserDetailsService myUserDetailsService;

    final private JwtService jwtService;

    public JwtFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver, MyUserDetailsService myUserDetailsService, JwtService jwtService) {
        this.exceptionResolver = exceptionResolver;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorisationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if(authorisationHeader != null && authorisationHeader.startsWith("Bearer ")){
            jwt = authorisationHeader.substring(7);
            try{
                username = jwtService.extractUsername(jwt);
            }
            catch (JwtException e){
            exceptionResolver.resolveException(request, response, null, e);
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
            if(jwtService.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
