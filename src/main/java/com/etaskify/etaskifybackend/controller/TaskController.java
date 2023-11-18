package com.etaskify.etaskifybackend.controller;


import com.etaskify.etaskifybackend.dto.TaskDTO;
import com.etaskify.etaskifybackend.dto.TaskRequest;
import com.etaskify.etaskifybackend.enums.TaskStatus;
import com.etaskify.etaskifybackend.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Api for task management", description = "Use this api to perform CRUD operations on tasks")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Creates task", description = "It enables organization admin to create tasks and assign them to  of the organization")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createTask(@Valid @RequestBody TaskRequest taskRequest) {
        taskService.createTask(taskRequest);
    }

    @Operation(summary = "Updates task", description = "It enables organization admin to update task details")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateTaskById(@PathVariable(name = "id") long id,
                               @Valid @RequestBody TaskRequest taskRequest) throws AccessDeniedException {
        taskService.updateTask(id, taskRequest);
    }

    @Operation(summary = "Deletes task", description = "It enables organization admin to delete task")
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

    @Operation(summary = "Returns tasks of a user", description = "It returns the list of tasks assigned to a user")
    @GetMapping("/user-tasks/{id}")
    public List<TaskDTO> getAllTasksByUserId(@PathVariable(name = "id")long id){
        return taskService.getAllTasksByUserId(id);
    }

    @Operation(summary = "Returns tasks of an organization", description = "It returns the list of tasks of an organization that can be accessed by the signed in user of the organization")
    @GetMapping("/org/{orgId}")
    public List<TaskDTO> getAllTaskByOrgId(@PathVariable(name = "orgId") long id) {
        return taskService.getAllTasksByOrganizationId(id);
    }

    @Operation(summary = "Returns tasks by status", description = "It returns the list of tasks of an organization by status that can be accessed by the signed in user of the organization")
    @GetMapping("/status/")
    public List<TaskDTO> getTasksByStatusId(@RequestParam(name = "status") String status) {
        return taskService.getAllTasksByStatus(TaskStatus.valueOf(status));
    }
}
