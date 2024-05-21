package com.referAll.backend.controllers;

import com.referAll.backend.entities.dtos.LoginResponse;
import com.referAll.backend.entities.dtos.LoginUserDto;
import com.referAll.backend.entities.dtos.RegisterUserDto;
import com.referAll.backend.entities.dtos.UserDto;
import com.referAll.backend.entities.models.User;
import com.referAll.backend.services.AuthenticationService;
import com.referAll.backend.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody UserDto registerUserDto) {
        System.out.println(registerUserDto);
        UserDto registeredUserDto = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            String jwtToken = jwtService.generateToken(authenticatedUser);
            System.out.println("jwt- " + jwtToken);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            // Handle authentication exception and return error response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}