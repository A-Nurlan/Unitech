package com.example.unitech.resource.authentication;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRefreshRequest {

    @NotBlank
    private String refreshToken;

}
