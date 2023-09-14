package com.us.worlddetails.service;

import com.us.worlddetails.dto.CountryCodeDTO;
import com.us.worlddetails.dto.CountryDetailsDTO;
import com.us.worlddetails.dto.SearchDTO;
import com.us.worlddetails.dto.UserRequest;
import com.us.worlddetails.entity.*;
import com.us.worlddetails.repo.CityRepo;
import com.us.worlddetails.repo.CountryRepo;
import com.us.worlddetails.repo.LanguageRepo;
import com.us.worlddetails.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    OTPService otpService;
    @Autowired
    CityRepo cityRepo;
    @Autowired
    LanguageRepo languageRepo;
    @Autowired
    CountryRepo countryRepo;
    private final Random random = new Random();
    private static final String ERROR = "error";

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

        User newUser = new User(email, userRequest.getFirstName(), userRequest.getLastName(), Gender.valueOf(userRequest.getGender()), null);
        userRepo.save(newUser);
        return "redirect:/?success=true";
    }

    private boolean isNotValidEmail(String s) {
        return !Pattern.compile("^(.+)@(.+)$").matcher(s).matches();
    }

    public ResponseEntity<?> sendOtp(String email) {

        Optional<User> optionalUser = userRepo.findById(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        int otp = random.nextInt(1000, 9999);
        boolean isSent = otpService.sendOtp(email, otp);
        if (isSent) {
            User user = optionalUser.get();
            user.setOtp(new BCryptPasswordEncoder()
                    .encode(String.valueOf(otp)));
            userRepo.save(user);
        }

        return isSent ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    public String viewDashboard(String search, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<User> userOptional = userRepo.findById(userDetails.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
        }

        if (!StringUtils.isEmpty(search))
            model.addAttribute("searchData", getAllData(search));

        return "dashboard";
    }

    public ResponseEntity<SearchDTO> searchData(String search) {
        return ResponseEntity.ok(getAllData(search));
    }

    private SearchDTO getAllData(String search) {
        SearchDTO searchDTO = new SearchDTO();
        List<CountryCodeDTO> data = countryRepo.findMatchingCountry(search);
        searchDTO.setCountryNames(data);

        List<String> data1 = cityRepo.findMatchingCity(search);
        searchDTO.setCityNames(data1);

        data1 = languageRepo.findMatchingLanguage(search);
        searchDTO.setLanguages(data1);
        return searchDTO;
    }

    public String viewCountryDetails(Model model, String code) {

        Country country = countryRepo.findCountryByCode(code);
        List<City> cities = cityRepo.findCityByCode(code);
        List<CountryLanguage> countryLanguages = languageRepo.findLangByCode(code);

        CountryDetailsDTO countryDetailsDTO = new CountryDetailsDTO();
        countryDetailsDTO.setCountry(country);
        countryDetailsDTO.setCities(cities);
        countryDetailsDTO.setCountryLanguages(countryLanguages);

        model.addAttribute("countryDetails", countryDetailsDTO);
        return "country-details";
    }
}
