package com.us.worlddetails.controller;

import com.us.worlddetails.entity.User;
import com.us.worlddetails.pojo.UserRequest;
import com.us.worlddetails.service.OTPService;
import com.us.worlddetails.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OTPService otpService;

    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        UserRequest user = new UserRequest();
        model.addAttribute("user", user);
        return "index";
    }

    @PostMapping("/register")
    public String userRegistration(@ModelAttribute UserRequest userRequest, Model model) {
        return userService.userRegistration(userRequest, model);
    }

    @PostMapping("/login")
    public String userLogin(@ModelAttribute UserRequest userRequest, Model model) {
        return userService.userLogin(userRequest, model);
    }

    @GetMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam(name = "email") String email) {
        if (StringUtils.isEmpty(email))
            return ResponseEntity.badRequest().build();

        boolean isSent = otpService.sendOtp(email);
        return isSent ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/world")
    public String viewWorldPage (Model model) {
        return "world";
    }
}
