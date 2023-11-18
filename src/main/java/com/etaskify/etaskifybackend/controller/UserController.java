package com.etaskify.etaskifybackend.controller;

import com.etaskify.etaskifybackend.dto.UserRequest;
import com.etaskify.etaskifybackend.dto.UserCreateResponse;
import com.etaskify.etaskifybackend.dto.UserDTO;
import com.etaskify.etaskifybackend.service.UserService;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;


    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest userRequest) {
        UserCreateResponse response = userService.addUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserDTO> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateUser(@PathVariable long  id,
                           @Valid @RequestBody UserRequest userRequest) {
        userService.updateUserById(id, userRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
    }


}
