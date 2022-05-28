package com.example.unitech.service.impl;

import com.example.unitech.config.JwtConfig;
import com.example.unitech.data.entity.User;
import com.example.unitech.data.repository.UserRepository;
import com.example.unitech.exception.InvalidFieldException;
import com.example.unitech.resource.UserResource;
import com.example.unitech.resource.authentication.*;
import com.example.unitech.resource.authentication.TokenPair.TokenDetails;
import com.example.unitech.resource.mapper.UserMapper;
import com.example.unitech.service.AuthenticationService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final String TOKEN_DETAILS = "tokenDetails";
    public static final String INVALID_AUTHENTICATION_MESSAGE = "request is not properly authenticated";
    public static final String BAD_CREDENTIALS_MESSAGE = "username or password is incorrect";

    private static final String TOKEN_TYPE_ACCESS = "access";
    private static final String TOKEN_TYPE_REFRESH = "refresh";
    private static final String TOKEN_TYPE = "tokenType";
    private static final String USER_PIN = "pin";

    private JwtParser tokenParser;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtConfig jwtConfig;

    @PostConstruct
    public void init() {

        // initialize tokenParser ( tokenParser is used for both access tokens and refresh tokens )
        tokenParser = Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getTokenSignKey().getBytes(StandardCharsets.UTF_8))
                .build();
    }


      @Override
    public TokenPair token(TokenCreateRequest tokenCreateRequest) {
        // authenticate user with credentials
        User user = authenticateUser(tokenCreateRequest);


        // tokenPair is a container for access and refresh tokens
        TokenPair tokenPair = new TokenPair();

        tokenPair.setAccessToken(prepareAccessToken(user));


        return tokenPair;
    }

    private User authenticateUser(TokenCreateRequest tokenCreateRequest) {

        User user = userRepository.findUserByPin(tokenCreateRequest.getPin())
                .orElseThrow(() -> new BadCredentialsException(BAD_CREDENTIALS_MESSAGE));


        if (!passwordEncoder.matches(tokenCreateRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);
        }


        return user;
    }

    private TokenDetails prepareAccessToken(User user) {

        Instant expiry = Instant.now().plus(Duration.ofSeconds(jwtConfig.getAccessTokenDurationSeconds()));

        return TokenDetails.builder()
                .expiry(expiry)
                .content(Jwts.builder()
                        .setIssuedAt(new Date())
                        .setExpiration(Date.from(expiry))
                        .claim(USER_PIN, user.getPin())
                        .claim(TOKEN_TYPE, TOKEN_TYPE_ACCESS)
                        .signWith(Keys.hmacShaKeyFor(jwtConfig.getTokenSignKey().getBytes(StandardCharsets.UTF_8)))
                        .compact())
                .build();
    }

    public UserResource register(UserRegistrationRequest request) {

        /*
            validate user request data, most of validations is done by annotation based validation rules
            validation method is used for handling complex validation which
         */
        validateRegistrationRequest(request);

        // prepare user data
        User user = new User();
        user.setPin(request.getPin());

        // encode user password with bcrypt encoding
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);


        // prepare resource links
        return userMapper.to(user);
    }

    private void validateRegistrationRequest(UserRegistrationRequest request) {
        if (userRepository.findUserByPin(request.getPin()).isPresent()) {
            throw new InvalidFieldException("pin", "this pin is already used by another user");
        }
    }



    // preparing authentication object for spring security
    @Override
    public Optional<TokenAuthentication> getTokenAuthentication(String token) {
        try {
            Jwt<?, Claims> jwtData = tokenParser.parseClaimsJws(token);
            Claims body = jwtData.getBody();

            String tokenType = body.get(TOKEN_TYPE, String.class);

            /* only accept access tokens, refresh token should be ignored
               we have same signing key for access and refresh tokens, so an attacker may use refresh token to get some data
               by checking token type we prevent token misuse
             */

            if (!TOKEN_TYPE_ACCESS.equals(tokenType)) {
                return Optional.empty();
            }

            /*
             we build Authentication object (TokenAuthentication) from jwt details, no database touch needed
             user role is used as token authority which will be used by checking user privileges by spring security
             */
            return Optional.of(TokenAuthentication.builder()
                    .name(body.get(USER_PIN, String.class))
                    .authorities(Collections.singleton(UserAuthority.builder()
                            .build()))
                    .principal(jwtData)
                    .details(body.get(USER_PIN))
                    .build());
        } catch (JwtException e) { // if something is wrong with jwt parsing and authenticating
            // we will return null which means that authentication is finished with no success
        }

        // just return empty to claim that authentication is finished
        return Optional.empty();
    }

    @Override
    public TokenUserDetails getTokenInfo() {

        TokenAuthentication tokenAuthentication = getCurrentAuthentication()
                .orElseThrow(() -> new InsufficientAuthenticationException(INVALID_AUTHENTICATION_MESSAGE));


        Jwt<?, ?> jwtData = (Jwt<?, ?>) tokenAuthentication.getPrincipal();
        Claims claims = (Claims) jwtData.getBody();

        User user = userRepository.findUserByPin(claims.get(USER_PIN, String.class)).orElseThrow();


        return TokenUserDetails.builder()
                .user(userMapper.to(user))
                .creationTime(claims.getIssuedAt().toInstant())
                .expirationTime(claims.getExpiration().toInstant())
                .build();
    }

    // this method is used by services to get current authenticated user by authentication
    @Override
    public User getCurrentUser() {
        TokenAuthentication tokenAuthentication = getCurrentAuthentication()
                .orElseThrow(() -> new InsufficientAuthenticationException(INVALID_AUTHENTICATION_MESSAGE));

        Jwt<?, ?> jwtData = (Jwt<?, ?>) tokenAuthentication.getPrincipal();
        Claims claims = (Claims) jwtData.getBody();

        // return user from userId which is stored in jwt token
        return userRepository.findUserByPin(claims.get(USER_PIN, String.class)).orElseThrow();
    }

    public Optional<TokenAuthentication> getCurrentAuthentication() {
        // read current authentication from spring security context
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(item -> item instanceof TokenAuthentication)
                .map(item -> (TokenAuthentication) item);
    }
}
