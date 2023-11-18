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
@Schema(description = "Represents the response coming from user creation operation")
public class UserCreateResponse {
    private String email;
    private String password;
}
