package com.softserve.edu.dto;

import com.softserve.edu.model.Role;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateOrUpdateUserRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private String firstName;

    private String lastName;

    private String role;

    public CreateOrUpdateUserRequest() {
    }

    public CreateOrUpdateUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
