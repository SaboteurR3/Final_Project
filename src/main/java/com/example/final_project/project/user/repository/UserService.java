package com.example.final_project.project.user.repository;

import com.example.final_project.project.auth.repository.LoginDTO;
import com.example.final_project.project.user.repository.entity.User;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO getUserByName(String username);
    LoginDTO getUserByPassword(String password);
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
    void saveUser(User user);
}
