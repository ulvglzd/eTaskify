package com.etaskify.etaskifybackend.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Represents the request to create task and update tasks details")
public class TaskRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    private LocalDateTime deadline;
    private List<Long> assignId;
}
