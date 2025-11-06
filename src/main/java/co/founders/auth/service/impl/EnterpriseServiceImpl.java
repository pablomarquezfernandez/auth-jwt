package co.founders.auth.service.impl;

import co.founders.auth.dto.EnterpriseDTO;
import co.founders.auth.model.Enterprise;
import co.founders.auth.model.Status;
import co.founders.auth.repository.EnterpriseRepository;
import co.founders.auth.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import co.founders.auth.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public EnterpriseDTO createEnterprise(EnterpriseDTO enterpriseDTO) {
        enterpriseDTO.setId( UUID.randomUUID() );
        Enterprise enterprise = modelMapper.map(enterpriseDTO, Enterprise.class);
        enterprise.setStatus(Status.ACTIVE);
        Enterprise savedEnterprise = enterpriseRepository.save(enterprise);
        return modelMapper.map(savedEnterprise, EnterpriseDTO.class);
    }

    @Override
    public EnterpriseDTO getEnterprise(java.util.UUID id) throws EntityNotFoundException{
        System.out.println("Getting enterprise with ID: " + id);
        return enterpriseRepository.findById(id)
                .map(e -> modelMapper.map(e, EnterpriseDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada."));
    }

    @Override
    public List<EnterpriseDTO> getAllEnterprises() {
        return enterpriseRepository.findAll().stream()
                .map(e -> modelMapper.map(e, EnterpriseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EnterpriseDTO updateEnterprise(java.util.UUID id, EnterpriseDTO enterpriseDTO) throws EntityNotFoundException{
        Optional<Enterprise> optionalEnterprise = enterpriseRepository.findById(id);
        if (optionalEnterprise.isPresent()) {
            Enterprise enterprise = optionalEnterprise.get();
            enterprise.setStatus(Status.ACTIVE);
            modelMapper.map(enterpriseDTO, enterprise);
            Enterprise updatedEnterprise = enterpriseRepository.save(enterprise);
            return modelMapper.map(updatedEnterprise, EnterpriseDTO.class);
        }else{
            throw new EntityNotFoundException("Empresa no encontrada.");
        }
    }

    @Override
    public void deleteEnterprise(java.util.UUID id) throws EntityNotFoundException {
        Enterprise enterprise = enterpriseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada."));
        enterprise.setStatus(Status.INACTIVE);
        enterpriseRepository.save(enterprise);
    }
}
