package co.founders.auth.service;

import java.util.List;

import co.founders.auth.dto.EnterpriseDTO;

public interface EnterpriseService {
    EnterpriseDTO createEnterprise(EnterpriseDTO enterpriseDTO);
    EnterpriseDTO getEnterprise(java.util.UUID id) throws co.founders.auth.exception.EntityNotFoundException;
    List<EnterpriseDTO> getAllEnterprises();
    EnterpriseDTO updateEnterprise(java.util.UUID id, EnterpriseDTO enterpriseDTO) throws co.founders.auth.exception.EntityNotFoundException;
    void deleteEnterprise(java.util.UUID id) throws co.founders.auth.exception.EntityNotFoundException;
}
