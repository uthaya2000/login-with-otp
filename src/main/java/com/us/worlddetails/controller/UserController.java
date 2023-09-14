package com.us.worlddetails.controller;

import com.us.worlddetails.dto.SearchDTO;
import com.us.worlddetails.dto.UserRequest;
import com.us.worlddetails.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@Controller
public class UserController {

    @Autowired
    UserService userService;

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

    @GetMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam(name = "email") String email) {
        if (StringUtils.isEmpty(email))
            return ResponseEntity.badRequest().build();

        return userService.sendOtp(email);
    }

    @GetMapping("/dashboard")
    public String viewWorldPage (@RequestParam(value = "search", defaultValue = "") String search
            , Model model) {

        return userService.viewDashboard(search, model);
    }

    @GetMapping("/world")
    public ResponseEntity<SearchDTO> searchData(@RequestParam("search") String search) {
        return userService.searchData(search);
    }

    @GetMapping("/world/{code}")
    public String viewCountryDetails(Model model, @PathVariable("code") String code) {
        return userService.viewCountryDetails(model, code);
    }
}
