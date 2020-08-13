package com.softserve.edu.dto;

import com.softserve.edu.model.Role;
import com.softserve.edu.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

//@Builder
@Data
public class UserResponse {
    private Long id;

    @NotEmpty
    private String email;

    private String firstName;

    private String lastName;
    private String name;


    private RoleResponse role;


    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.name = user.getFirstName() + " " + user.getLastName();
        this.role = new RoleResponse(user.getRole());
    }
}
