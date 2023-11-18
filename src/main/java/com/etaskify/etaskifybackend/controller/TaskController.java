package com.etaskify.etaskifybackend.controller;


import com.etaskify.etaskifybackend.dto.TaskDTO;
import com.etaskify.etaskifybackend.dto.TaskRequest;
import com.etaskify.etaskifybackend.enums.TaskStatus;
import com.etaskify.etaskifybackend.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createTask(@Valid @RequestBody TaskRequest taskRequest) {
        taskService.createTask(taskRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateTaskById(@PathVariable(name = "id") long id,
                               @Valid @RequestBody TaskRequest taskRequest) throws AccessDeniedException {
        taskService.updateTask(id, taskRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskById(@PathVariable(name = "id") long id) throws AccessDeniedException {
        taskService.deleteTask(id);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable(name = "id") long id) throws AccessDeniedException {
        return taskService.getTaskById(id);
    }

    @GetMapping("/user-tasks/{id}")
    public List<TaskDTO> getAllTasksByUserId(@PathVariable(name = "id")long id){
        return taskService.getAllTasksByUserId(id);
    }

    @GetMapping("/org/{orgId}")
    public List<TaskDTO> getAllTaskByOrgId(@PathVariable(name = "orgId") long id) {
        return taskService.getAllTasksByOrganizationId(id);
    }

    @GetMapping("/status/")
    public List<TaskDTO> getTasksByStatusId(@RequestParam(name = "status") String status) {
        return taskService.getAllTasksByStatus(TaskStatus.valueOf(status));
    }
}
