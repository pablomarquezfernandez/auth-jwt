package co.founders.auth.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.core.JsonProcessingException;

import co.founders.auth.dto.UserDTO;
import co.founders.auth.dto.UserExtendDTO;
import co.founders.auth.exception.EntityNotFoundException;
import jakarta.mail.MessagingException;
import co.founders.auth.dto.AuthenticationDTO;
import co.founders.auth.dto.AuthorizedDTO;
import co.founders.auth.dto.UserCreateDTO;

public interface AppUserService {
    
    
    
    UserExtendDTO createUser(UserCreateDTO userCreateDTO) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, MessagingException ;
    UserDTO getUser(java.util.UUID id) throws co.founders.auth.exception.EntityNotFoundException;
    List<UserDTO> getAllUsers();
    UserDTO updateUser(java.util.UUID id, UserDTO userDTO) throws co.founders.auth.exception.EntityNotFoundException;
    void deleteUser(java.util.UUID id) throws co.founders.auth.exception.EntityNotFoundException;
    AuthorizedDTO login(AuthenticationDTO authenticationDTO)  throws JsonProcessingException, EntityNotFoundException;
    void startPasswordRecovery(String email)throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, MessagingException;
    void activateUser(String token) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;
    void resetPassword(String token, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;

}