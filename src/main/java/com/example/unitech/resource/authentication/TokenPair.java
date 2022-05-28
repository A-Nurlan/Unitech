package com.example.unitech.resource.authentication;

import lombok.*;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenPair {

    private TokenDetails accessToken;
    private TokenDetails refreshToken;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenDetails {
        private String content;
        private Instant expiry;
    }

}
