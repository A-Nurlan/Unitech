package com.example.unitech.service;

import com.example.unitech.resource.UserDto;

public interface UserService {
    void create(UserDto requestBody);

    void deleteUser(String pin);
}
