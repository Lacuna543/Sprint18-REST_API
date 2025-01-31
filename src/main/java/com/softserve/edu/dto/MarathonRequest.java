package com.softserve.edu.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MarathonRequest {
    @NotEmpty
    private String title;
}