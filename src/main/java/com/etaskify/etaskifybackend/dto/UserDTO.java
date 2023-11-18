package com.etaskify.etaskifybackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Represents the user details")
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private Set<TaskDTO> tasks = new HashSet<>();
}
