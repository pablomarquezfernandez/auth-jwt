package co.founders.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.founders.auth.dto.EnterpriseDTO;
import co.founders.auth.service.EnterpriseService;

@RestController
@RequestMapping("/api/auth/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    @PostMapping
    public EnterpriseDTO createEnterprise(  @RequestBody EnterpriseDTO enterpriseDTO) {
        return enterpriseService.createEnterprise(enterpriseDTO);
    }

    @GetMapping("/{id}")
    public EnterpriseDTO getEnterprise(Authentication authentication, @PathVariable java.util.UUID id) {
        if( authentication == null || !authentication.isAuthenticated()) {
            throw new co.founders.auth.exception.NotAuthenticationException("Usuario no autenticado");
        }
        return enterpriseService.getEnterprise(id);
    }

    @GetMapping
    public List<EnterpriseDTO> getAllEnterprises(Authentication authentication) {
        if( authentication == null || !authentication.isAuthenticated()) {
            throw new co.founders.auth.exception.NotAuthenticationException("Usuario no autenticado");
        }
        return enterpriseService.getAllEnterprises();
    }

    @PutMapping("/{id}")
    public EnterpriseDTO updateEnterprise(Authentication authentication, @PathVariable java.util.UUID id, @RequestBody EnterpriseDTO enterpriseDTO) {
        if( authentication == null || !authentication.isAuthenticated()) {
            throw new co.founders.auth.exception.NotAuthenticationException("Usuario no autenticado");
        }
        return enterpriseService.updateEnterprise(id, enterpriseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEnterprise( Authentication authentication, @PathVariable java.util.UUID id) {
        if( authentication == null || !authentication.isAuthenticated()) {
            throw new co.founders.auth.exception.NotAuthenticationException("Usuario no autenticado");
        }
        enterpriseService.deleteEnterprise(id);
    }
}
