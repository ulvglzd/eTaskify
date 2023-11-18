package com.etaskify.etaskifybackend.service;

import com.etaskify.etaskifybackend.dto.OrganizationDTO;
import com.etaskify.etaskifybackend.dto.CreateOrganizationRequest;

public interface OrganizationService {
    OrganizationDTO saveOrganization(CreateOrganizationRequest request);
    OrganizationDTO getOrganizationById(Long id);

}
