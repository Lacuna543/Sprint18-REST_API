package com.softserve.edu.service;

import com.softserve.edu.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);

    Role getRoleByName(String name);

    List<Role> getAll();
}
