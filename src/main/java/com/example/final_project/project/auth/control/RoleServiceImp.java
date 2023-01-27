package com.example.final_project.project.auth.control;

import com.example.final_project.project.auth.repository.entity.Role;
import com.example.final_project.project.auth.repository.RoleRepository;
import com.example.final_project.project.auth.repository.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class RoleServiceImp implements RoleService {
    RoleRepository roleRepository;
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
