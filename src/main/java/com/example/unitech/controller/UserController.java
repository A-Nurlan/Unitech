package com.example.unitech.controller;

import com.example.unitech.resource.UserDto;
import com.example.unitech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void create(@RequestBody UserDto requestBody){
        userService.create(requestBody);
    }

    @DeleteMapping("/{pin}")
    public void delete(@PathVariable String pin){
        userService.deleteUser(pin);
    }
}
