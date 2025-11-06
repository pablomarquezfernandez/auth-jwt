package co.founders.auth.service.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.tomcat.util.http.parser.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.founders.auth.dto.AuthenticationDTO;
import co.founders.auth.dto.AuthorizedDTO;
import co.founders.auth.dto.EnterpriseDTO;
import co.founders.auth.dto.UserCreateDTO;
import co.founders.auth.dto.UserDTO;
import co.founders.auth.dto.UserExtendDTO;
import co.founders.auth.exception.EntityNotFoundException;
import co.founders.auth.exception.InvalidAuthenticationException;
import co.founders.auth.exception.InvalidCodeException;
import co.founders.auth.exception.UserInactiveException;
import co.founders.auth.model.AppUser;
import co.founders.auth.model.Enterprise;
import co.founders.auth.model.Role;
import co.founders.auth.model.Status;
import co.founders.auth.repository.UserRepository;
import co.founders.auth.security.JwtUtil;
import co.founders.auth.service.AppUserService;
import co.founders.auth.service.EnterpriseService;
import co.founders.auth.util.EmailUtil;
import jakarta.mail.MessagingException;

@Service
public class AppUserServiceImpl implements AppUserService {
   
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnterpriseService enterpriseService;
    
       

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    EmailUtil emailUtil;

    @Override
    public UserExtendDTO createUser(UserCreateDTO userCreateDTO) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, MessagingException {
        String password = jwtUtil.hashSHA1( userCreateDTO.getPassword() );
        userCreateDTO.setPassword( password );
        AppUser user = modelMapper.map(userCreateDTO, AppUser.class);
        user.setStatus(Status.ACTIVE);
        user.setRegistrationStatus( Status.INACTIVE);
        user.setRole( Role.USER );
        //
        // Create enterprise
        EnterpriseDTO enterprise = userCreateDTO.getEnterprise();
        enterprise = enterpriseService.createEnterprise(enterprise);
        userCreateDTO.setEnterprise(enterprise);
        user.setEnterprise( modelMapper.map(enterprise, Enterprise.class) );

        AppUser savedUser = userRepository.save(user);
        userCreateDTO.setId( savedUser.getId() );
        sendActivationEmail(userCreateDTO);
        

        return modelMapper.map(savedUser, UserExtendDTO.class);
    }

    private void sendActivationEmail(UserCreateDTO userCreateDTO) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, MessagingException {

        java.nio.file.Path path = java.nio.file.Paths.get("src/main/resources/email-activacion.html");
        String html = java.nio.file.Files.readString(path);

        String token = jwtUtil.encrypt( "AC;" +userCreateDTO.getEmail() + ";" + Calendar.getInstance().getTime(),  jwtUtil.generateKey());
        String urlActivacion = System.getenv( "ACTIVATION_URL" ) + token;


        html = html.replace("${nombreUsuario}", userCreateDTO.getFirstname());
        html = html.replace("${urlActivacion}", urlActivacion);

        emailUtil.sendEmail(userCreateDTO.getEmail(), "Activación de cuenta", html);
    }

    @Override
    public UserDTO getUser(java.util.UUID id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .map(u -> modelMapper.map(u, UserDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> modelMapper.map(u, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(java.util.UUID id, UserDTO userDTO) throws EntityNotFoundException{
        Optional<AppUser> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            Enterprise enterprise = user.getEnterprise();
            String currentPassword = user.getPassword();
            modelMapper.map(userDTO, user);
            user.setPassword(currentPassword);
            user.setStatus(Status.ACTIVE);
            user.setEnterprise(enterprise);

            AppUser updatedUser = userRepository.save(user);
            return modelMapper.map(updatedUser, UserDTO.class);
        }else{
            throw new EntityNotFoundException("Usuario no encontrado.");
        }
    }

    

    @Override
    public void deleteUser(java.util.UUID id) throws EntityNotFoundException {
        AppUser user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));
        user.setStatus(Status.INACTIVE);
        userRepository.save(user);
    }

    @Override
    public AuthorizedDTO login(AuthenticationDTO authenticationDTO) throws JsonProcessingException, EntityNotFoundException {
        String email = authenticationDTO.getEmail();
        String password =  jwtUtil.hashSHA1(authenticationDTO.getPassword());


        AppUser user = userRepository.findAppUserByEmailAndPassword(email, password);
        if (user == null || user.getStatus() != Status.ACTIVE) {
            throw new InvalidAuthenticationException("Credenciales inválidas o usuario inactivo.");
        }
        if( user.getRegistrationStatus() != Status.ACTIVE ){
            throw new UserInactiveException("Usuario no activado. Revisa tu correo electrónico.");

        }
        
        UserExtendDTO userExtendDTO = modelMapper.map(user, UserExtendDTO.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(userExtendDTO);
        String token = jwtUtil.generateToken(userJson, user.getRole().name());



        AuthorizedDTO authorizedDTO = new AuthorizedDTO();
        authorizedDTO.setUser(userExtendDTO);
        authorizedDTO.setToken( token );
        // Si quieres retornar el JSON, puedes usar: return userJson;
        // Si solo quieres el objeto, el JSON está disponible en userJson
        return authorizedDTO;
    }

     public void activateUser(String token) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String text = jwtUtil.decrypt(token, jwtUtil.generateKey());
        String[] parts = text.split(";");
        if( parts[0] == null || !parts[0].equals("AC")){
            throw new InvalidCodeException( "Código inválido" );
        }
        AppUser userOpt = userRepository.findAppUserByEmail(parts[1]);
        if (userOpt == null) {
            throw new InvalidCodeException( "Código inválido" );
        }
        
        userOpt.setRegistrationStatus(Status.ACTIVE);
        userRepository.save(userOpt);
    }


     /**
     * Inicia el proceso de recuperación de contraseña: genera token, envía email y guarda token temporal.
     * @throws MessagingException 
     */
    public void startPasswordRecovery(String email) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, MessagingException {
        AppUser user = userRepository.findAppUserByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("Usuario no encontrado para recuperación.");
        }
        
        // Generar token seguro (puedes usar UUID o cifrado similar al de activación)
        String token = jwtUtil.encrypt("RP;" + user.getEmail() + ";" + Calendar.getInstance().getTime(), jwtUtil.generateKey());
        String urlRecuperacion = System.getenv( "RESTART_PASSWORD_URL" ) + token;
        // Guardar token temporal en el usuario (puedes agregar campo en AppUser si lo deseas)
        // user.setRecoveryToken(token); // Si agregas el campo
        // userRepository.save(user);
        // Leer plantilla HTML
        java.nio.file.Path path = java.nio.file.Paths.get("src/main/resources/email-recuperacion.html");
        String html = java.nio.file.Files.readString(path);
        html = html.replace("${nombreUsuario}", user.getFirstname());
        html = html.replace("${urlRecuperacion}", urlRecuperacion);
        // Enviar email
        emailUtil.sendEmail(user.getEmail(), "Recuperación de contraseña", html);
    }


    public void resetPassword(String token, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        System.out.println( token );
        String text = jwtUtil.decrypt(token, jwtUtil.generateKey());
        String[] parts = text.split(";");
        if( parts[0] == null || !parts[0].equals("RP")){
            throw new InvalidCodeException( "Código inválido" );
        }
        AppUser userOpt = userRepository.findAppUserByEmail(parts[1]);
        if (userOpt == null) {
            throw new InvalidCodeException( "Código inválido" );
        }
        password = jwtUtil.hashSHA1(password);
        userOpt.setPassword(password);
        userRepository.save(userOpt);
    }


}
