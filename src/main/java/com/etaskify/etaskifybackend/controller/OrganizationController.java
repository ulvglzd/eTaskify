package com.etaskify.etaskifybackend.controller;


import com.etaskify.etaskifybackend.dto.OrganizationDTO;
import com.etaskify.etaskifybackend.dto.OrganizationRequest;
import com.etaskify.etaskifybackend.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/organizations")
@RequiredArgsConstructor
@Tag(name = "Api for organization", description = "Use this api to get and update your organization details")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Operation(summary = "Returns organization details", description = "It returns the details of the organization of the signed in user")
    @GetMapping("/{id}")
    public OrganizationDTO getById(@PathVariable long id) {
        return organizationService.getOrganizationById(id);
    }

    @Operation(summary = "Updates organization details", description = "It enables organization admin to update organization details")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateOrganization(@PathVariable long id,
                                   @Valid @RequestBody OrganizationRequest organizationRequest) {
        organizationService.updateOrganization(id, organizationRequest);
    }

}
