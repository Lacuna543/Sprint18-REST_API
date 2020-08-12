package com.softserve.edu.dto;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MarathonInfo {
    private String title;

    public MarathonInfo(Marathon marathon) {
        this.title = marathon.getTitle();
    }

    public MarathonInfo() {
    }
}
