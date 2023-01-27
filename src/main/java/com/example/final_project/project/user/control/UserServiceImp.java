package com.example.final_project.project.user.control;

import com.example.final_project.project.auth.repository.LoginDTO;
import com.example.final_project.project.user.repository.UserRepository;
import com.example.final_project.project.user.repository.UserService;
import com.example.final_project.project.user.repository.UserDTO;
import com.example.final_project.project.user.repository.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService {
    UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.getUserById(id);
    }
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserDTO getUserByName(String username) {
        return userRepository.getUserByName(username);
    }

    @Override
    public LoginDTO getUserByPassword(String password) {
        return userRepository.findByPassword(password);
    }

    @Override
    public Boolean existsByName(String username) {
        return userRepository.existsByName(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
