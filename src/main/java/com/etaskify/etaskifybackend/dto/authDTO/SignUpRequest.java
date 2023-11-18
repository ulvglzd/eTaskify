package com.etaskify.etaskifybackend.dto.authDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @Size(min = 6, max = 50, message = "The minimum password length must be 6")
    private String password;
    @NotBlank(message = "Organization name cannot be empty")
    private String organizationName;
    private String organizationAddress;
    private String organizationPhoneNumber;

}
