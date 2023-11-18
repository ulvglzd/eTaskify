package com.etaskify.etaskifybackend.controller;

import com.etaskify.etaskifybackend.dto.UserRequest;
import com.etaskify.etaskifybackend.dto.UserCreateResponse;
import com.etaskify.etaskifybackend.dto.UserDTO;
import com.etaskify.etaskifybackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Api for user management", description = "Use this api to perform CRUD operations on users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Creates user", description = "It enable organization admin to create user")
    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest userRequest) {
        UserCreateResponse response = userService.addUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Return users", description = "It only returns the list of users that belong to the organization of the signed in user")
    @GetMapping
    public List<UserDTO> getAllUser() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get a user by ID", description = "Returns a single user")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Updates user details", description = "It enables organization admin to update user details")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateUser(@PathVariable long  id,
                           @Valid @RequestBody UserRequest userRequest) {
        userService.updateUserById(id, userRequest);
    }

    @Operation(summary = "Deletes a user", description = "It enables organization admin to delete a user")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
    }


}
