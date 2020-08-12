package com.softserve.edu.dto;

import javax.validation.constraints.NotEmpty;

import com.softserve.edu.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class UserInfo {

    private String email;

    private String firstName;

    private String lastName;

    private String role;

    public UserInfo() {
    }

    public UserInfo(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole().toString();
    }


}
