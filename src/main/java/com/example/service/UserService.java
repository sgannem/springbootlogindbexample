package com.example.service;

import com.example.model.User1;
import com.example.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User1 findByEmail(String email);

    User1 save(UserRegistrationDto registration);
}
