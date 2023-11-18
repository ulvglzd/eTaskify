package com.etaskify.etaskifybackend.service;

import com.etaskify.etaskifybackend.dto.OrganizationDTO;
import com.etaskify.etaskifybackend.dto.OrganizationRequest;

public interface OrganizationService {
    OrganizationDTO getOrganizationById(Long id);
    public void updateOrganization(Long id, OrganizationRequest request);

}
