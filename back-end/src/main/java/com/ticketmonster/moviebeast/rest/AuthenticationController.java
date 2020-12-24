package com.ticketmonster.moviebeast.rest;

import com.ticketmonster.moviebeast.util.security.JwtAuthenticationRequest;
import com.ticketmonster.moviebeast.util.security.JwtTokenUtil;
import com.ticketmonster.moviebeast.util.security.JwtUser;
import com.ticketmonster.moviebeast.util.security.exceptions.AuthenticationException;
import com.ticketmonster.moviebeast.util.security.service.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class AuthenticationController extends AbstractController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @PostMapping("${jwt.route.authentication.path}")
    public JwtAuthenticationResponse createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtAuthenticationResponse(token);
    }

    @PostMapping("${jwt.route.authentication.refresh}")
    public JwtAuthenticationResponse refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        String refreshedToken = jwtTokenUtil.refreshToken(token);
        return new JwtAuthenticationResponse(refreshedToken);
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
}
