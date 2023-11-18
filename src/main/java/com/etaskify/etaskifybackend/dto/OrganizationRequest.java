package com.etaskify.etaskifybackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRequest {
    @NotBlank
    private String organizationName;
    private String address;
    private String phoneNumber;
}
