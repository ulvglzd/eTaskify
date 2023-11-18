package com.etaskify.etaskifybackend.service.Impl;

import com.etaskify.etaskifybackend.dto.UserCreateRequest;
import com.etaskify.etaskifybackend.dto.UserCreateResponse;
import com.etaskify.etaskifybackend.enums.Role;
import com.etaskify.etaskifybackend.exception.AlreadyExistsException;
import com.etaskify.etaskifybackend.exception.EntityNotFoundException;
import com.etaskify.etaskifybackend.model.User;
import com.etaskify.etaskifybackend.repository.UserRepository;
import com.etaskify.etaskifybackend.service.UserService;
import com.etaskify.etaskifybackend.service.auth.AuthService;
import com.etaskify.etaskifybackend.utility.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Override
    public UserCreateResponse addUser(UserCreateRequest userCreateRequest) {

        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new AlreadyExistsException("User with this email already exists");
        }

        User currentAdmin = authService.getSignedInUser();

        User user = new User();
        user.setEmail(userCreateRequest.getEmail());
        user.setName(userCreateRequest.getName());
        user.setSurname(userCreateRequest.getSurname());
        String password = passwordGenerator.generatePassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        user.setOrganization(currentAdmin.getOrganization());
        user.setUsername(userCreateRequest.getEmail());
        userRepository.save(user);

        return UserCreateResponse.builder()
                .email(user.getEmail())
                .password(password)
                .build();
    }



}
