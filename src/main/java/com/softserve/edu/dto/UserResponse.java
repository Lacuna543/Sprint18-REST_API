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

    private Role role;

    private Long userId;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.userId = user.getId();
    }
}
