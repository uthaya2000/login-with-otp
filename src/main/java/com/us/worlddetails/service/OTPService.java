package com.us.worlddetails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPService {

    @Autowired
    JavaMailSender mailSender;

    public boolean sendOtp(String email, int otp) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("geoworld@gmail.com");
        simpleMailMessage.setSubject("Geo World OTP Password");
        simpleMailMessage
                .setText("OTP Password: " + otp + ". This OTP will expire in 5 minutes.");
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

}
