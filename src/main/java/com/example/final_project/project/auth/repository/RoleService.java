package com.example.final_project.project.auth.repository;

import com.example.final_project.project.auth.repository.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findRoleByName(String name);
}
