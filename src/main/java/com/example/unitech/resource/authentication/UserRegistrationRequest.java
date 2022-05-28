package com.example.unitech.resource.authentication;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationRequest {

    @NotNull
    private String pin;

    @NotBlank
    @Length(min = 6, max = 255)
    private String password;


}
