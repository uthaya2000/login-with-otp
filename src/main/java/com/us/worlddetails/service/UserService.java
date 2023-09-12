package com.us.worlddetails.service;

import com.us.worlddetails.entity.Gender;
import com.us.worlddetails.entity.User;
import com.us.worlddetails.pojo.UserRequest;
import com.us.worlddetails.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    OTPService otpService;
    @Autowired
    AuthenticationManager authenticationManager;

    private static final String ERROR ="error";

    public String userRegistration(UserRequest userRequest, Model model) {
        Optional<User> duplicateUser = userRepo.findById(userRequest.getEmail());
        if (duplicateUser.isPresent()) {
            model.addAttribute(ERROR, "User Already Present");
            return "redirect:/";
        }

        String email = userRequest.getEmail();

        if (isNotValidEmail(email)) {
            model.addAttribute(ERROR, "Email is not valid");
            return "redirect:/";
        }

        User newUser = new User(email, userRequest.getFirstName(), userRequest.getLastName(), Gender.valueOf(userRequest.getGender()));
        userRepo.save(newUser);
        return "redirect:/?success=true";
    }

    public String userLogin(UserRequest userRequest, Model model) {
        Optional<User> user = userRepo.findById(userRequest.getEmail());
        if(user.isEmpty()
                || !otpService.verifyOtp(userRequest.getEmail(), userRequest.getOtp())) {
            model.addAttribute(ERROR, "Invalid Credentials");
            return "redirect:/";
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), "1234", Collections.EMPTY_LIST));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/?success";
    }

    private boolean isNotValidEmail(String s) {
        return !Pattern.compile("^(.+)@(.+)$").matcher(s).matches();
    }
}
