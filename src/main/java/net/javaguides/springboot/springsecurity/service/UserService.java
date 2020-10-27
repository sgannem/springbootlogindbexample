package net.javaguides.springboot.springsecurity.service;

import net.javaguides.springboot.springsecurity.model.User1;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.javaguides.springboot.springsecurity.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    User1 findByEmail(String email);

    User1 save(UserRegistrationDto registration);
}
