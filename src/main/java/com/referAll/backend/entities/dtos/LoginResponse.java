package com.referAll.backend.entities.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String firstName;
    private String lastName;
    private String emailId;
    private String contactNumber;
    private String currentTitle;
    private String currentCompany;
    private String resumeUrl;
    private String linkedInUrl;
    private String bio;
    private Long points=0L;
    private String jwtToken;
    private long expiresIn;
}