package com.etaskify.etaskifybackend.repository;

import com.etaskify.etaskifybackend.enums.TaskStatus;
import com.etaskify.etaskifybackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByAssignedUsersId(Long userId);
    List<Task> findAllByStatus(TaskStatus status);
    List<Task> findAllByOrganizationId(Long organizationId);
}
