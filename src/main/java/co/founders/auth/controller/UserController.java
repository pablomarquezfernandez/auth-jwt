package co.founders.auth.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.founders.auth.dto.UserDTO;
import co.founders.auth.dto.UserExtendDTO;
import co.founders.auth.dto.UserCreateDTO;
import co.founders.auth.service.AppUserService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/auth/user")
public class UserController {
    
    @Autowired
    private AppUserService userService;

        

    @PostMapping
    public UserExtendDTO createUser(@RequestBody UserCreateDTO userCreateDTO) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, MessagingException {
        return userService.createUser(userCreateDTO);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(Authentication authentication, @PathVariable java.util.UUID id) throws co.founders.auth.exception.EntityNotFoundException {
        if( authentication == null || !authentication.isAuthenticated()) {
            throw new co.founders.auth.exception.NotAuthenticationException("Usuario no autenticado");
        }
        return userService.getUser(id);
    }

    @GetMapping
    public List<UserDTO> getAllUsers(Authentication authentication) {
        if( authentication == null || !authentication.isAuthenticated()) {
            throw new co.founders.auth.exception.NotAuthenticationException("Usuario no autenticado");
        }
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(Authentication authentication, @PathVariable java.util.UUID id, @RequestBody UserCreateDTO userDTO) throws co.founders.auth.exception.EntityNotFoundException {
        if( authentication == null || !authentication.isAuthenticated()) {
            throw new co.founders.auth.exception.NotAuthenticationException("Usuario no autenticado");
        }
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(Authentication authentication, @PathVariable java.util.UUID id) throws co.founders.auth.exception.EntityNotFoundException {
        if( authentication == null || !authentication.isAuthenticated()) {
            throw new co.founders.auth.exception.NotAuthenticationException("Usuario no autenticado");
        }
        userService.deleteUser(id);
    }

    @GetMapping("/activate")
    public void activateUser(@RequestParam("token") String token) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        userService.activateUser(token);
    }

    @PostMapping("/recover-password")
    public void recoverPassword(@RequestParam String email) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, MessagingException {
        userService.startPasswordRecovery(email);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestParam String token, @RequestParam String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, MessagingException {
        System.out.println( token );
        System.out.println( password );
        userService.resetPassword(token, password);
    }
}
