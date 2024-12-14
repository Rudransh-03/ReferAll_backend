package com.referAll.backend.controllers;

import com.referAll.backend.entities.dtos.PostDto;
import com.referAll.backend.services.OTPService;
import com.referAll.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
public class OTPController {

    @Autowired
    private OTPService otpService;

    @PostMapping("/auth/generate-otp")
    public ResponseEntity<String> generateOTP(@RequestBody Map<String, String> request) {
        System.out.println(request);
        String email = request.get("emailId");
        String otp = otpService.generateOTP(email);
        return ResponseEntity.ok(otp);
    }

    @PostMapping("/auth/validate-otp")
    public ResponseEntity<String> validateOTP(@RequestBody Map<String, String> request) {
        String email = request.get("emailId");
        String otp = request.get("OTP");
        String newPassword = request.get("password");

        System.out.println("Input email: "+email);
        System.out.println("Input otp: "+otp);

        if (otpService.validateOTP(email, otp, newPassword)) {
            return ResponseEntity.ok("OTP is valid!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
        }
    }
}
