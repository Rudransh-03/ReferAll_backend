package com.referAll.backend.services;

import com.referAll.backend.entities.dtos.LoginUserDto;
import com.referAll.backend.entities.dtos.UserDto;
import com.referAll.backend.entities.models.User;
import com.referAll.backend.respositories.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public static final int ID_LENGTH = 15;

    @Autowired
    private ModelMapper modelMapper;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto signup(UserDto newUserDto) {
        String id = generateUniqueId();
        while (userRepository.existsById(id)) {
            id = generateUniqueId();
        }
        newUserDto.setUserId(id);
        newUserDto.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        User newUser = modelMapper.map(newUserDto, User.class);
        userRepository.save(newUser);

        UserDto registeredUserDto = modelMapper.map(newUser, UserDto.class);

        return registeredUserDto;

    }

    public User authenticate(LoginUserDto input) throws Exception {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
            return userRepository.findByEmailId(input.getEmail())
                    .orElseThrow(() -> new Exception("User not found with email"));
        } catch (AuthenticationException e) {
            throw new Exception("Please ensure you enter the registered email and password.");
        }
    }

    public String generateUniqueId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }
}