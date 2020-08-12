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

    public UserRequest(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
