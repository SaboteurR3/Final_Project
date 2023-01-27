package com.example.final_project.project.auth.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationDTO {
    @NotBlank(message = "Username mustn't be empty!")
    @Size(max = 350)
    @Pattern(regexp = "[a-zA-Z0-9]{1,}")
    private String name;
    @NotBlank(message = "Email address mustn't be empty!")
    @Email(message = "Inlaid email address")
    @Size(max = 350)
    private String email;
    @NotBlank(message = "Password mustn't be empty!")
    @Size(min = 8, max = 150)
    @Pattern(regexp = "[a-zA-Z0-9_!?+=]{1,}")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
