package com.etaskify.etaskifybackend.dto;

import com.etaskify.etaskifybackend.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Represents task details in the system")
public class TaskDTO {
    private String title;
    private String description;
    private TaskStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    private LocalDateTime deadline;
}
