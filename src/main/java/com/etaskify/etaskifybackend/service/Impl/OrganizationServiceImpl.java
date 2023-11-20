package com.etaskify.etaskifybackend.service.Impl;

import com.etaskify.etaskifybackend.dto.OrganizationDTO;
import com.etaskify.etaskifybackend.dto.OrganizationRequest;
import com.etaskify.etaskifybackend.exception.EntityNotFoundException;
import com.etaskify.etaskifybackend.exception.NotAllowedException;
import com.etaskify.etaskifybackend.model.Organization;
import com.etaskify.etaskifybackend.repository.OrganizationRepository;
import com.etaskify.etaskifybackend.service.OrganizationService;
import com.etaskify.etaskifybackend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @Override
    public OrganizationDTO getOrganizationById(Long id) {
        return organizationRepository
                .findById(id)
                .map(org -> modelMapper.map(org, OrganizationDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
    }

    @Override
    public void updateOrganization(Long id, OrganizationRequest request) {
        Long loggedInAdmin = authService.getSignedInUser().getOrganization().getId();
        if (!loggedInAdmin.equals(id)) {
            throw new NotAllowedException("You are not allowed to update this organization");
        }
        Organization org = organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
        org = Organization.builder()
                .id(id)
                .name(request.getOrganizationName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .users(org.getUsers())
                .task(org.getTask())

                .build();
        organizationRepository.save(org);
    }

}

