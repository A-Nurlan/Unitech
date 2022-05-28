package com.example.unitech.service.impl;

import com.example.unitech.data.entity.User;
import com.example.unitech.data.repository.UserRepository;
import com.example.unitech.resource.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    public UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void createAlreadyExist() {
        UserDto requestBody = new UserDto();

        User user=new User();

        when(userRepository.findUserByPin(requestBody.getPin())).thenReturn(Optional.of(user));

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.create(requestBody),
                "User is already exists !");
    }


}