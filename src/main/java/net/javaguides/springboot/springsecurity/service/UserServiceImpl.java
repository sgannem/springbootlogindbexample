package net.javaguides.springboot.springsecurity.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import net.javaguides.springboot.springsecurity.model.User1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.springsecurity.model.Role;
import net.javaguides.springboot.springsecurity.repository.UserRepository;
import net.javaguides.springboot.springsecurity.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User1 findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User1 save(UserRegistrationDto registration){
        User1 user1 = new User1();
        user1.setFirstName(registration.getFirstName());
        user1.setLastName(registration.getLastName());
        user1.setEmail(registration.getEmail());
        user1.setPassword(passwordEncoder.encode(registration.getPassword()));
        user1.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user1);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User1 user1 = userRepository.findByEmail(email);
        if (user1 == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user1.getEmail(),
                user1.getPassword(),
                mapRolesToAuthorities(user1.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
