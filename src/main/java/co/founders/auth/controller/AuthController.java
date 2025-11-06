package co.founders.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import co.founders.auth.dto.AuthenticationDTO;
import co.founders.auth.dto.AuthorizedDTO;
import co.founders.auth.dto.UserDTO;
import co.founders.auth.exception.InvalidTokenException;
import co.founders.auth.security.JwtUtil;
import co.founders.auth.service.AppUserService;

@RestController
@RequestMapping("/api/auth/auth")
public class AuthController {
    @Autowired
    private AppUserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthorizedDTO login(@RequestBody AuthenticationDTO authenticationDTO) throws co.founders.auth.exception.EntityNotFoundException, JsonProcessingException {
        System.out.println("AuthController - /login called");
        AuthorizedDTO result = userService.login(authenticationDTO);
        return result;
    }

    @PostMapping("/renew-token")
    public AuthorizedDTO renewToken(Authentication authentication) throws JsonMappingException, JsonProcessingException{
        System.out.println( "Renew token ok" );
        return jwtUtil.renewToken(authentication);
    }

    @GetMapping("/validate")
    public String validate(@RequestParam String token) {
        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);
            return "Token válido para: " + email + ", rol: " + role;
        }
        return "Token inválido";
    }

    @PostMapping("/authenticate")
    public UserDTO authenticate(Authentication authentication) throws InvalidTokenException {

        try {
            return jwtUtil.getUser(authentication);
        } catch (Exception e) {
        }
        throw new InvalidTokenException("Token inválido o expirado");
    }


    @PostMapping("/app-authenticate")
    public AuthorizedDTO appAuthenticate(@RequestParam String token) throws InvalidTokenException {

        try {
            return jwtUtil.getAuthorized(token);
        } catch (Exception e) {
        }
        throw new InvalidTokenException("Token inválido o expirado");
    }

}
