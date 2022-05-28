package com.example.unitech.controller;

import com.example.unitech.resource.UserResource;
import com.example.unitech.resource.authentication.*;
import com.example.unitech.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.unitech.constant.ApiConstants.*;


@RestController
@RequestMapping(API_AUTHENTICATION)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping(PATH_TOKEN)
    @ResponseStatus(HttpStatus.CREATED)
    public TokenPair createToken(@RequestBody @Validated TokenCreateRequest tokenCreateRequest) {
        return service.token(tokenCreateRequest);
    }

    @GetMapping(PATH_TOKEN)
    public TokenUserDetails getToken() {
        return service.getTokenInfo();
    }


}
