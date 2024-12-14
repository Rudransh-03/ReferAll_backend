package com.referAll.backend.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OTPDetails {
    private final String otp;
    private final String emailId;
    private final LocalDateTime expiryTime;
}

