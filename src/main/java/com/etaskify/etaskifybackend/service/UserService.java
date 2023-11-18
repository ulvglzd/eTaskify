package com.etaskify.etaskifybackend.service;


import com.etaskify.etaskifybackend.dto.UserRequest;
import com.etaskify.etaskifybackend.dto.UserCreateResponse;
import com.etaskify.etaskifybackend.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserCreateResponse addUser(UserRequest userRequest);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    void deleteUserById(Long id);
    void updateUserById(Long id, UserRequest userRequest);

}
