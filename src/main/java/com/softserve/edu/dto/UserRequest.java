package com.softserve.edu.dto;

import javax.validation.constraints.NotEmpty;

import com.softserve.edu.model.Role;
import com.softserve.edu.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRequest {
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private String firstName;

    private String lastName;

    private Role role;

    private Long userId;

    public UserRequest(@NotEmpty String email, @NotEmpty String password) {
        this.email = email;
        this.password = password;
    }

    public UserRequest(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.userId = user.getId();
    }
}
