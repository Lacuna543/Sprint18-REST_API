package com.softserve.edu.dto;

import com.softserve.edu.model.Role;
import com.softserve.edu.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoleResponse {

    private String roleName;

    public RoleResponse(Role role) {
        this.roleName = role.getName();
    }
}
