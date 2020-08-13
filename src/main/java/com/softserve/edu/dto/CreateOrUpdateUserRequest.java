package com.softserve.edu.dto;

import lombok.*;

@AllArgsConstructor
@Data
public class CreateOrUpdateUserRequest {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String role;


}
