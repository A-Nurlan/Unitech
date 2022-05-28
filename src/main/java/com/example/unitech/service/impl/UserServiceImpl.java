package com.example.unitech.service.impl;

import com.example.unitech.data.entity.User;
import com.example.unitech.data.repository.UserRepository;
import com.example.unitech.resource.UserDto;
import com.example.unitech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        // create default user if not exists
        if (userRepository.findUserByPin("19931993").isEmpty()) {
            UserDto userDto = new UserDto();
            userDto.setPin("19931993");
            userDto.setPassword("19931993");
            create(userDto);
        }
    }

    @Override
    public void create(UserDto requestBody) {
        if (userRepository.findUserByPin(requestBody.getPin()).isPresent())
            throw new IllegalArgumentException("User is already exists !");

        User user = new User();
        user.setPin(requestBody.getPin());
        user.setPassword(passwordEncoder.encode(requestBody.getPassword()));
        user.setRegistrationDate(new Date());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String pin) {
        Optional<User> user = userRepository.findUserByPin(pin);
        if (user.isEmpty())
            throw new IllegalArgumentException("User not found !");

        userRepository.delete(user.get());
    }

}
