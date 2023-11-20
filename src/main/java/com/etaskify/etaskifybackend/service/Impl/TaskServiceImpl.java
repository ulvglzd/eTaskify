package com.etaskify.etaskifybackend.service.Impl;

import com.etaskify.etaskifybackend.dto.TaskDTO;
import com.etaskify.etaskifybackend.dto.TaskRequest;
import com.etaskify.etaskifybackend.enums.TaskStatus;
import com.etaskify.etaskifybackend.exception.EntityNotFoundException;
import com.etaskify.etaskifybackend.exception.NotAllowedException;
import com.etaskify.etaskifybackend.model.Organization;
import com.etaskify.etaskifybackend.model.Task;
import com.etaskify.etaskifybackend.model.User;
import com.etaskify.etaskifybackend.repository.OrganizationRepository;
import com.etaskify.etaskifybackend.repository.TaskRepository;
import com.etaskify.etaskifybackend.repository.UserRepository;
import com.etaskify.etaskifybackend.service.TaskService;
import com.etaskify.etaskifybackend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final AuthService authService;
    private final MailSendingService mailSendingService;

    @Override
    public void createTask(TaskRequest request) {
        Organization organization = organizationRepository
                .findByUsersUserEmail(authService.getSignedInUser().getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));

        Set<User> assignedUsers = new HashSet<>();
        for (Long userId : request.getAssignId()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            if (!user.getOrganization().equals(organization)) {
                throw new NotAllowedException("You are not allowed to assign this user");
            }
            assignedUsers.add(user);
            mailSendingService.sendMail(user.getEmail(), "New task",
                    "You have been assigned to a new task, visit your dashboard to see more details");
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .status(TaskStatus.PENDING)
                .assignedUsers(assignedUsers)
                .organization(organization)
                .build();

        taskRepository.save(task);
        log.info("Task created successfully");

    }

    @Override
    public TaskDTO getTaskById(Long taskId) throws NotAllowedException {
        return  mapTaskToDto(getByTaskId(taskId));
    }

    @Override
    public List<TaskDTO> getAllTasksByOrganizationId(Long organizationId) {
        checkIfOrgMatches(organizationId);
        List<TaskDTO> tasks = taskRepository.findAllByOrganizationId(organizationId)
                .stream()
                .map(this::mapTaskToDto)
                .collect(Collectors.toList());
        return tasks;
    }


    @Override
    public List<TaskDTO> getAllTasksByUserId(Long userId) {
        List<TaskDTO> tasks = taskRepository.findAllByAssignedUsersId(userId)
                .stream()
                .map(this::mapTaskToDto)
                .collect(Collectors.toList());
        return tasks;
    }

    @Override
    public List<TaskDTO> getAllTasksByStatus(TaskStatus status) {
        List<TaskDTO> tasks = taskRepository
                .findAllByOrganizationId(authService.getSignedInUser()
                        .getOrganization()
                        .getId())
                .stream()
                .filter(task -> task.getStatus().equals(status))
                .map(this::mapTaskToDto)
                .collect(Collectors.toList());
        return tasks;
    }

    @Override
    public void updateTask(Long taskId, TaskRequest request) throws NotAllowedException {
        Task task = getByTaskId(taskId);
        task = Task.builder()
                .id(taskId)
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .status(TaskStatus.valueOf(request.getStatus().toUpperCase()))
                .organization(task.getOrganization())
                .build();
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) throws NotAllowedException {
        taskRepository.delete(getByTaskId(taskId));
    }

    private Task getByTaskId(Long taskId) throws NotAllowedException {
        Organization signedInUserOrganization = authService.getSignedInUser().getOrganization();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task was not found"));

        if (!task.getOrganization().equals(signedInUserOrganization)) {
            throw new NotAllowedException("You are not allowed to access this task");
        }
        else
            return task;

    }

    private void checkIfOrgMatches(Long organizationId) {
        Long signedInUserOrgId = authService.getSignedInUser().getOrganization().getId();
        if (signedInUserOrgId != organizationId) {
            throw new NotAllowedException("You are not allowed to access this organization");
        }
    }

    private TaskDTO mapTaskToDto(Task task) {
        return TaskDTO.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .build();
    }

}
