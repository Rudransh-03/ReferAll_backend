package com.referAll.backend.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserDto {

    private String userId;

    private String firstName;

    private String lastName;

    private String emailId;

    private String password;

    private String contactNumber;

    private String currentTitle;

    private String currentCompany;

    private String resumeUrl;

    private String linkedInUrl;

    private String bio;

    private Long points=0L;
}
