package com.etaskify.etaskifybackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Represents a request to update organization details in the system")
public class OrganizationRequest {
    @NotBlank
    private String organizationName;
    private String address;
    private String phoneNumber;
}
