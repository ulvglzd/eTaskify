package com.etaskify.etaskifybackend.dto.authDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInRequest {
    @Email
    private String email;
    @Size(min = 6, max = 50, message = "The minimum password length must be 6")
    private String password;
}
