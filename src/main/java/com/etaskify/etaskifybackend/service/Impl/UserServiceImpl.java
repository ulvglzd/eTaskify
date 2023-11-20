package com.etaskify.etaskifybackend.service.Impl;

import com.etaskify.etaskifybackend.dto.UserCreateResponse;
import com.etaskify.etaskifybackend.dto.UserDTO;
import com.etaskify.etaskifybackend.dto.UserRequest;
import com.etaskify.etaskifybackend.enums.Role;
import com.etaskify.etaskifybackend.exception.AlreadyExistsException;
import com.etaskify.etaskifybackend.exception.EntityNotFoundException;
import com.etaskify.etaskifybackend.exception.NotAllowedException;
import com.etaskify.etaskifybackend.model.User;
import com.etaskify.etaskifybackend.repository.UserRepository;
import com.etaskify.etaskifybackend.service.UserService;
import com.etaskify.etaskifybackend.service.auth.AuthService;
import com.etaskify.etaskifybackend.utility.PasswordGenerator;
import com.etaskify.etaskifybackend.utility.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserMapper userMapper;


    @Override
    public UserCreateResponse addUser(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AlreadyExistsException("User with this email already exists");
        }

        User currentAdmin = authService.getSignedInUser();

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        String password = passwordGenerator.generatePassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        user.setOrganization(currentAdmin.getOrganization());
        user.setUsername(userRequest.getUsername());
        userRepository.save(user);

        return UserCreateResponse.builder()
                .email(user.getEmail())
                .password(password)
                .build();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        User signedInUser = authService.getSignedInUser();
        List<UserDTO> users = userRepository.findAllByOrganizationId(signedInUser
                .getOrganization()
                .getId())
                .stream()
                .map(userMapper::mapUserToUserDTO)
                .collect(Collectors.toList());
        return users;
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = getUserOrThrow(userRepository.findById(id));
        return userMapper.mapUserToUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = getUserOrThrow(userRepository.findByEmail(email));
        return userMapper.mapUserToUserDTO(user);
    }

    @Override
    public void deleteUserById(Long id) {
        getUserOrThrow(userRepository.findById(id));
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserById(Long id, UserRequest userRequest) {
        User user = getUserOrThrow(userRepository.findById(id));
        user = User.builder()
                .id(user.getId())
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .password(user.getPassword())
                .role(user.getRole())
                .organization(user.getOrganization())
                .username(userRequest.getUsername())
                .tasks(user.getTasks())
                .build();
        userRepository.save(user);
    }

    private User getUserOrThrow(Optional<User> optionalUser) {
        Long SignedInUserOrgId = getCurrentOrganizationId();
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!user.getOrganization().getId().equals(SignedInUserOrgId)) {
            throw new NotAllowedException("You are not allowed to access this user");
        }
        return user;
    }

    private Long getCurrentOrganizationId() {
        return authService.getSignedInUser()
                .getOrganization()
                .getId();
    }




}
