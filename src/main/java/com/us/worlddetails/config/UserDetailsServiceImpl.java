package com.us.worlddetails.config;

import com.us.worlddetails.entity.User;
import com.us.worlddetails.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findById(username);

        if (user.isPresent()) {
            User us = user.get();
            return new org.springframework.security.core.userdetails
                    .User(us.getEmail(),us.getOtp(), Collections.emptyList());
        }

        throw new UsernameNotFoundException("User not Available");
    }
}
