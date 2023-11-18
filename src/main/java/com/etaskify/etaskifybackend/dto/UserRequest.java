package com.etaskify.etaskifybackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Represents the request to create user and update user details")
public class UserRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
}
