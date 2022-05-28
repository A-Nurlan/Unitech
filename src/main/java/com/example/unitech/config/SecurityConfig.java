package com.example.unitech.config;


import com.example.unitech.component.AuthorizationFilter;
import com.example.unitech.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.unitech.constant.ApiConstants.API_AUTHENTICATION;
import static com.example.unitech.constant.ApiConstants.PATH_ANT_MATCH_ALL;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http.cors()
                .disable()
                .csrf()
                .disable();

        // Set session management to stateless
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Our public endpoints
                // allow authentication endpoints to be called publicly
                .antMatchers(API_AUTHENTICATION + PATH_ANT_MATCH_ALL).permitAll()
                .antMatchers("/h2-console/**").permitAll()
                // require authentication by default
                .anyRequest().authenticated();

        http.addFilterBefore(new AuthorizationFilter(authenticationService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
