package co.founders.auth.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.founders.auth.dto.ErrorMessageDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserInactiveException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserInactiveException(UserInactiveException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.FORBIDDEN.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

    @ExceptionHandler(InvalidAuthenticationException.class)
    public ResponseEntity<ErrorMessageDTO> handleInvalidAuthenticationException(InvalidAuthenticationException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<ErrorMessageDTO> handleInvalidCodeException(InvalidCodeException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessageDTO> handleInvalidTokenException(InvalidTokenException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
    @ExceptionHandler(java.io.IOException.class)
    public ResponseEntity<ErrorMessageDTO> handleIOException(java.io.IOException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage("Error de entrada/salida: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(java.security.InvalidKeyException.class)
    public ResponseEntity<ErrorMessageDTO> handleInvalidKeyException(java.security.InvalidKeyException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage("Clave inválida: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(java.security.NoSuchAlgorithmException.class)
    public ResponseEntity<ErrorMessageDTO> handleNoSuchAlgorithmException(java.security.NoSuchAlgorithmException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage("Algoritmo no encontrado: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(javax.crypto.NoSuchPaddingException.class)
    public ResponseEntity<ErrorMessageDTO> handleNoSuchPaddingException(javax.crypto.NoSuchPaddingException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage("Padding no encontrado: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(javax.crypto.IllegalBlockSizeException.class)
    public ResponseEntity<ErrorMessageDTO> handleIllegalBlockSizeException(javax.crypto.IllegalBlockSizeException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage("Tamaño de bloque ilegal: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(javax.crypto.BadPaddingException.class)
    public ResponseEntity<ErrorMessageDTO> handleBadPaddingException(javax.crypto.BadPaddingException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage("Padding incorrecto: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
    @ExceptionHandler(EntityCreationException.class)
    public ResponseEntity<ErrorMessageDTO> handleEntityCreationException(EntityCreationException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.NOT_FOUND.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(NotAuthenticationException.class)
    public ResponseEntity<ErrorMessageDTO> handleNotAuthenticationException(NotAuthenticationException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessageDTO> handleAuthenticationException(AuthenticationException ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        ErrorMessageDTO errorMessage = new ErrorMessageDTO();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setTimestamp(LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
