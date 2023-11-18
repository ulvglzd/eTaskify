package com.etaskify.etaskifybackend.utility;

import com.etaskify.etaskifybackend.dto.TaskDTO;
import com.etaskify.etaskifybackend.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDTO mapTaskToDto(Task task) {
        return TaskDTO.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .build();
    }
}
