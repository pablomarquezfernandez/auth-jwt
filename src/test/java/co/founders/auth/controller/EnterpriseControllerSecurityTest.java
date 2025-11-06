package co.founders.auth.controller;

import co.founders.auth.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.context.annotation.Import;
import co.founders.auth.security.SecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnterpriseController.class)
@Import(SecurityConfig.class)
public class EnterpriseControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private co.founders.auth.service.EnterpriseService enterpriseService;

        @Test
        @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    void adminCanAccessEnterprises() throws Exception {
        mockMvc.perform(get("/auth/enterprise").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

        @Test
        @WithMockUser(username = "regularUser", roles = {"USER"})
    void userCanAccessEnterprises() throws Exception {
        mockMvc.perform(get("/auth/enterprise").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
