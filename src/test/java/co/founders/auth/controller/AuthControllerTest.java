package co.founders.auth.controller;

import co.founders.auth.dto.UserDTO;
import co.founders.auth.model.AppUser;
import co.founders.auth.model.Role;
import co.founders.auth.repository.UserRepository;
import co.founders.auth.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.anyString;

import org.springframework.context.annotation.Import;
import co.founders.auth.security.SecurityConfig;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@Import(SecurityConfig.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void testLoginSuccess() throws Exception {
    AppUser user = new AppUser();
        user.setEmail("test@example.com");
        user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("1234"));
        user.setRole(Role.ADMIN);
        Mockito.when(userRepository.findAll()).thenReturn(java.util.List.of(user));
        Mockito.when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("token123");

        String json = "{\"email\":\"test@example.com\",\"password\":\"1234\"}";
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("token123"));
    }

    @Test
    void testLoginFail() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(java.util.List.of());
        String json = "{\"email\":\"wrong@example.com\",\"password\":\"badpass\"}";
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Credenciales inválidas"));
    }


    @Test
    void testRefreshTokenSuccess() throws Exception {
        String email = "test@example.com";
        String role = "ADMIN";
        String originalToken = jwtUtil.generateToken(email, role);

        // Mock Authentication principal
        org.springframework.security.core.Authentication authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(originalToken, null);
        Mockito.when(jwtUtil.renewToken(authentication)).thenReturn(new co.founders.auth.dto.AuthorizedDTO(null, "refreshed-token-123", "Bearer"));

        // Aquí deberías adaptar el controlador para aceptar Authentication si es necesario
        // Si el endpoint sigue usando el token como String, solo verifica el mock
        mockMvc.perform(post("/auth/refresh")
                .param("token", originalToken))
                .andExpect(status().isOk())
                .andExpect(content().string("refreshed-token-123"));
    }

    @Test
    void testRefreshTokenInvalid() throws Exception {
    String invalidToken = "invalid.token.value";
    org.springframework.security.core.Authentication authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(invalidToken, null);
    Mockito.when(jwtUtil.renewToken(authentication))
        .thenThrow(new IllegalArgumentException("Token inválido o expirado"));

    mockMvc.perform(post("/auth/refresh")
        .param("token", invalidToken))
        .andExpect(status().isOk())
        .andExpect(content().string("Error: Token inválido o expirado"));
    }
}
