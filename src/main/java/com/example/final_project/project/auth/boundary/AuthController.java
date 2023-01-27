package com.example.final_project.project.auth.boundary;

import com.example.final_project.project.auth.repository.RegistrationDTO;
import com.example.final_project.project.auth.control.AuthService;
import com.example.final_project.project.auth.repository.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDto) {
        authService.login(loginDto);
        return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDTO registrationDTO) {
        authService.register(registrationDTO);
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
