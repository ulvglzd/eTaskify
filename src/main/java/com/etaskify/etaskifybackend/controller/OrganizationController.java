package com.etaskify.etaskifybackend.controller;


import com.etaskify.etaskifybackend.dto.OrganizationDTO;
import com.etaskify.etaskifybackend.dto.OrganizationRequest;
import com.etaskify.etaskifybackend.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/{id}")
    public OrganizationDTO getById(@PathVariable long id) {
        return organizationService.getOrganizationById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateOrganization(@PathVariable long id,
                                   @Valid @RequestBody OrganizationRequest organizationRequest) {
        organizationService.updateOrganization(id, organizationRequest);
    }

}
