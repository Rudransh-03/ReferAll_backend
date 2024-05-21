package com.referAll.backend.entities.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginUserDto {
    private String email;

    private String password;

    // getters and setters here...
}