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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
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
        // ?????????????????????????????? ??????????????? ???????????????????????????????????? ?????? ???????????????????????? ?????? ?????? ?????????????????? ??????????????????
        User user = userRepository.getUserByUsername(loginDto.getName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(user == null) {
            throw new LoginException("Something went wrong!");
        } else {
            boolean passwordMatches = encoder.matches(
                    loginDto.getPassword(),
                    user.getPassword()
            );
            if (!passwordMatches) {
                throw new LoginException("Something went wrong!");
            }
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
