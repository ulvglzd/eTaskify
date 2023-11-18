package com.etaskify.etaskifybackend.service;

import com.etaskify.etaskifybackend.dto.TaskRequest;
import com.etaskify.etaskifybackend.dto.TaskDTO;
import com.etaskify.etaskifybackend.enums.TaskStatus;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface TaskService {
    void createTask(TaskRequest request);
    TaskDTO getTaskById(Long taskId) throws AccessDeniedException;
    List<TaskDTO> getAllTasksByOrganizationId(Long organizationId);
    List<TaskDTO> getAllTasksByUserId(Long userId);
    List<TaskDTO> getAllTasksByStatus(TaskStatus status);
    void updateTask(Long taskId, TaskRequest request) throws AccessDeniedException;
    void deleteTask(Long taskId) throws AccessDeniedException;
}
