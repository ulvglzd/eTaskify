package com.etaskify.etaskifybackend.utility;

import com.etaskify.etaskifybackend.dto.TaskDTO;
import com.etaskify.etaskifybackend.dto.UserDTO;
import com.etaskify.etaskifybackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final TaskMapper taskMapper;

    public UserDTO mapUserToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .tasks(user.getTasks()
                        .stream()
                        .map(taskMapper::mapTaskToDto)
                        .collect(Collectors.toSet()))
                .build();

    }
}
