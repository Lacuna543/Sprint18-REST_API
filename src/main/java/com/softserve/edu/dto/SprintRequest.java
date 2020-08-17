package com.softserve.edu.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.softserve.edu.model.Sprint;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class SprintRequest {

    @NotEmpty
    String title;

    @NotEmpty
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;

    @NotEmpty
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;

}
