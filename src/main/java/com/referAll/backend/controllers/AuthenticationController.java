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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        System.out.println(loginUserDto.getEmailId());
        System.out.println(loginUserDto.getPassword());

        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
//            System.out.println("HIHIIHIHIHI");
//            System.out.println(authenticatedUser.getFirstName());
            String jwtToken = jwtService.generateToken(authenticatedUser);
            System.out.println(jwtToken);
//            System.out.println("jwt- " + jwtToken);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setFirstName(authenticatedUser.getFirstName());
            loginResponse.setLastName(authenticatedUser.getLastName());
            loginResponse.setEmailId(authenticatedUser.getEmailId());
            loginResponse.setContactNumber(authenticatedUser.getContactNumber());
            loginResponse.setCurrentCompany(authenticatedUser.getCurrentCompany());
            loginResponse.setCurrentTitle(authenticatedUser.getCurrentTitle());
            loginResponse.setLinkedInUrl(authenticatedUser.getLinkedInUrl());
            loginResponse.setResumeUrl(authenticatedUser.getResumeUrl());
            loginResponse.setPoints(authenticatedUser.getPoints());
            loginResponse.setBio(authenticatedUser.getBio());
            loginResponse.setJwtToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            System.out.println("jwt Error");
            // Handle authentication exception and return error response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}