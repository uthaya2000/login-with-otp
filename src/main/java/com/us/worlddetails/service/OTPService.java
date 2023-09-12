package com.us.worlddetails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class OTPService {

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    OTPGenerator otpGenerator;

    public boolean sendOtp(String email) {
        String otpValue = otpGenerator.generateOTP(email);
        if (StringUtils.isEmpty(otpValue)) {
            return false;
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("geoworld@gmail.com");
        simpleMailMessage.setSubject("Geo World OTP Password");
        simpleMailMessage
                .setText("OTP Password: " + otpValue + ". This OTP will expire in 5 minutes.");
        simpleMailMessage.setTo(email);

        boolean isSent = false;
        try {
            mailSender.send(simpleMailMessage);
            isSent = true;
        } catch (Exception e) {
            /*Email send failed*/
        }

        return isSent;

    }


    public boolean verifyOtp(String email, String otp) {
        return otp.equals(otpGenerator.getOtpByKey(email));
    }


}
