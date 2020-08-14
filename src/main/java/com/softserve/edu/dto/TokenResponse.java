package com.softserve.edu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include. NON_NULL)
public class TokenResponse {

    private String token;
    private String error;

    public TokenResponse() {
    }
}
