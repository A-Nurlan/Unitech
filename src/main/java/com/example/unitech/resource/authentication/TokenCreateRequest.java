package com.example.unitech.resource.authentication;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class TokenCreateRequest {

    @NotBlank
    private String pin;

    @NotBlank
    private String password;

}
