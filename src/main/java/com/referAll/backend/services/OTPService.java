package com.referAll.backend.services;

public interface OTPService {

    String generateOTP(String emailId);

    boolean validateOTP(String emailId, String otp, String newPassword);
}
