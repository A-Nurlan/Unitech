package com.example.unitech.resource.authentication;

import com.example.unitech.resource.UserResource;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TokenUserDetails {

    private Instant creationTime;

    private Instant expirationTime;

    private UserResource user;
}
