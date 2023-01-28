package com.example.final_project.project.auth.control;

import com.example.final_project.exceptions.RegistrationException;
import com.example.final_project.project.auth.repository.LoginDTO;
import com.example.final_project.project.auth.repository.RegistrationDTO;
import com.example.final_project.exceptions.LoginException;
import com.example.final_project.project.auth.repository.entity.Role;
import com.example.final_project.project.user.repository.UserRepository;
import com.example.final_project.project.user.repository.entity.User;
import com.example.final_project.project.auth.repository.RoleService;
import com.example.final_project.project.user.repository.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
@Transactional
public class AuthService {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       UserService userService,
                       RoleService roleService,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void login(LoginDTO loginDto) {
        // შევამოწმოთ ასეთი მომხმარებელი თუ არსებობს და თუ პაროლი სწორია
        User user = userRepository.getUserByUsername(loginDto.getName());
        if (user == null ) {
            System.out.println(user.getPassword());
            System.out.println(passwordEncoder.encode(loginDto.getPassword()));
            throw new LoginException();
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getName(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void register(RegistrationDTO registrationDTO) {
        if (userService.existsByName(registrationDTO.getName())
                || userService.existsByEmail(registrationDTO.getEmail())) {
            throw new RegistrationException();
        }
        User user = new User();
        user.setName(registrationDTO.getName());
        user.setEmail(registrationDTO.getEmail());
        user.setBalance(BigDecimal.valueOf(500));
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        Role roles = roleService.findRoleByName("USER").orElse(null);
        user.setRoles(Collections.singletonList(roles));
        userService.saveUser(user);
    }
}
