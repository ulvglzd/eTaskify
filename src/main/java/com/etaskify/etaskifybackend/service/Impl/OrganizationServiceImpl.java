package com.etaskify.etaskifybackend.service.Impl;

import com.etaskify.etaskifybackend.dto.OrganizationDTO;
import com.etaskify.etaskifybackend.dto.CreateOrganizationRequest;
import com.etaskify.etaskifybackend.exception.EntityNotFoundException;
import com.etaskify.etaskifybackend.model.Organization;
import com.etaskify.etaskifybackend.repository.OrganizationRepository;
import com.etaskify.etaskifybackend.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrganizationDTO saveOrganization(CreateOrganizationRequest request) {
        Organization org = Organization.builder()
                .name(request.getOrganizationName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();
        organizationRepository.save(org);
        return modelMapper.map(org, OrganizationDTO.class);
    }

    @Override
    public OrganizationDTO getOrganizationById(Long id) {
        return organizationRepository
                .findById(id)
                .map(org -> modelMapper.map(org, OrganizationDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
    }

}

