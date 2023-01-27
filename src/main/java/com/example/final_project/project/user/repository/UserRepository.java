package com.example.final_project.project.user.repository;

import com.example.final_project.project.auth.repository.LoginDTO;
import com.example.final_project.project.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select new com.example.final_project.project.user.repository.UserDTO(u.id, u.name, u.email) from User u")
    List<UserDTO> getAllUsers();
    @Query("Select new com.example.final_project.project.user.repository.UserDTO(u.id, u.name, u.email)" +
            " from User u where u.id = :id")
    UserDTO getUserById(@Param("id") long id);
    @Query("Select new com.example.final_project.project.user.repository.UserDTO(u.id, u.name, u.email)" +
            " from User u where u.name = :name")
    UserDTO getUserByName(@Param("name") String name);

    @Query("Select u from User u where u.name = :name")
    User getUserByUsername(String name);
    @Query(value = "Select new com.example.final_project.project.auth.repository.LoginDTO(u.name, u.password)" +
            " from User u where u.password = :password")
    LoginDTO findByPassword(@Param("password") String password);
    Optional<User> findByName(String username);
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
}
