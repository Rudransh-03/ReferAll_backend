package com.referAll.backend.entities.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterUserDto {
    private String email;

    private String password;

    private String fullName;

    // getters and setters here...
}
