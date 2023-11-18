package com.etaskify.etaskifybackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Represents an organization in the system")
public class OrganizationDTO {
    private Long id;
    private String organizationName;
    private String phoneNumber;
    private String address;
}
