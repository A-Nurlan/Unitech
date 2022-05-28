package com.example.unitech.service;

import com.example.unitech.data.entity.User;
import com.example.unitech.resource.UserResource;
import com.example.unitech.resource.authentication.*;

import java.util.Optional;

public interface AuthenticationService {
    TokenPair token(TokenCreateRequest tokenCreateRequest);


    Optional<TokenAuthentication> getTokenAuthentication(String token);

    TokenUserDetails getTokenInfo();


    User getCurrentUser();
}
