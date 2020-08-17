package com.softserve.edu.dto;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Sprint;
import com.softserve.edu.model.User;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class MarathonResponse {

    private Long id;

    @NotEmpty
    private String title;

    private Set<Sprint> sprints;

    private Set<User> users;

    public MarathonResponse(Marathon marathon) {
        this.id = marathon.getId();
        this.title = marathon.getTitle();
        this.sprints = marathon.getSprints();
        this.users = marathon.getUsers();
    }
}
