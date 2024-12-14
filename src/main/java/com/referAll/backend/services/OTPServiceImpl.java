package com.referAll.backend.services;

import com.referAll.backend.entities.dtos.OTPDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OTPServiceImpl implements OTPService {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserService userService;

    private final Map<String, OTPDetails> otpStore = new HashMap<>();

    @Override
    public String generateOTP(String emailId) {
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000); // Generate a 4-digit OTP

        for(String key: otpStore.keySet()){
            if(otpStore.get(key).getOtp().equals(otp)){
                generateOTP(emailId);
            }
        }

        otpStore.put(emailId, new OTPDetails(otp, emailId, LocalDateTime.now().plusMinutes(10))); // Save OTP with expiry time

        System.out.println("entered? "+otpStore.get(emailId).getOtp());

        System.out.println(otp);
        emailSenderService.sendMail(emailId, "OTP Verification for ReferAll", "Someone (or you) just change the password for your account. Please enter the following OTP to confirm it was you and change the password for your account: \n"
                + "OTP: "+otp);

        return otp;
    }

    @Override
    public boolean validateOTP(String emailId, String otp, String newPassword) {
        System.out.println("line 33");

        for(String key: otpStore.keySet()) System.out.println(key+ " " +otpStore.get(key).getOtp());

        System.out.println(emailId);

        if(otpStore.containsKey(emailId)) System.out.println("present hai sarrr!!");

        if (!otpStore.containsKey(emailId)) return false;

        System.out.println("Inside validateOtp method");
//        System.out.println(otpStore.get(emailId));

        OTPDetails otpDetails = otpStore.get(emailId);
        if (otpDetails.getExpiryTime().isBefore(LocalDateTime.now())) {
            System.out.println("expired");
            otpStore.remove(emailId);
            return false; // OTP has expired
        }

        System.out.println("OTP: "+otp);
        System.out.println(otpDetails.getEmailId()+" "+otpDetails.getOtp());
        boolean isValid = otpDetails.getOtp().equals(otp);
        if (isValid){
            userService.changePassword(emailId, newPassword);
            otpStore.remove(emailId); // Remove OTP after successful validation
        }
        return isValid;
    }
}
