package com.softserve.edu.service;


import com.softserve.edu.dto.SprintRequest;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Sprint;

import java.util.List;

public interface SprintService {
    List<Sprint> getSprintsByMarathonId(Long id);

    Sprint addSprintToMarathon(SprintRequest sprintRequest, Marathon marathon);

     Sprint updateSprint(SprintRequest sprintRequest, Long marathonId, Marathon marathon);


        Sprint getSprintById(Long id);
}
