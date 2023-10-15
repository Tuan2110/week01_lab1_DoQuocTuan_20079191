package com.edu.iuh.fit.week1.services;

import com.edu.iuh.fit.week1.models.Role;
import com.edu.iuh.fit.week1.repositories.RoleRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoleService {
    RoleRepository roleRepository;
    public RoleService() {
        roleRepository = new RoleRepository();
    }
    public List<Role> getAllRoles() throws SQLException, ClassNotFoundException {
        return roleRepository.getAllRoles();
    }
    public Optional<Role> getRoleByName(String roleName) throws SQLException, ClassNotFoundException {
        return roleRepository.getRoleByName(roleName);
    }
}
