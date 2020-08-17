package com.softserve.edu.dto;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Sprint;
import com.softserve.edu.model.Task;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Data
public class SprintResponse {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private LocalDate startDate;

    @NotEmpty
    private LocalDate endDate;

    @NotEmpty
    private Marathon marathon;

    private List<Task> tasks;

    public SprintResponse(Sprint sprint) {
        this.id = sprint.getId();
        this.title = sprint.getTitle();
        this.startDate = sprint.getStartDate();
        this.endDate = sprint.getEndDate();
        this.marathon = sprint.getMarathon();
        this.tasks = sprint.getTasks();
    }
}
