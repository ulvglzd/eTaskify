package com.etaskify.etaskifybackend.service;


import com.etaskify.etaskifybackend.dto.UserCreateRequest;
import com.etaskify.etaskifybackend.dto.UserCreateResponse;

public interface UserService {
    UserCreateResponse addUser(UserCreateRequest userCreateRequest);
}
