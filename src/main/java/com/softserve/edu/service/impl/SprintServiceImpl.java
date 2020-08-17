package com.softserve.edu.service.impl;


import com.softserve.edu.dto.CreateOrUpdateUserRequest;
import com.softserve.edu.dto.SprintRequest;
import com.softserve.edu.dto.SprintResponse;
import com.softserve.edu.dto.UserResponse;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Sprint;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.repository.SprintRepository;
import com.softserve.edu.service.SprintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;
    private final MarathonRepository marathonRepository;

    public List<Sprint> getSprintsByMarathonId(Long id){
        return sprintRepository.getAllSprintsByMarathonId(id);
    }

    public Sprint addSprintToMarathon(SprintRequest sprintRequest, @NotNull Marathon marathon){
    Sprint sprint = new Sprint();
        return fillSprintFromSprintRequest(sprintRequest, sprint, marathon);


    }
    private Sprint fillSprintFromSprintRequest(SprintRequest sprintRequest, Sprint sprint, Marathon marathon){
        sprint.setStartDate(sprintRequest.getStartDate());
        sprint.setEndDate(sprintRequest.getEndDate());
        sprint.setTitle(sprintRequest.getTitle());
        sprint.setMarathon(marathon);
        sprintRepository.save(sprint);
        return sprint;
    }

    @Override
    public Sprint updateSprint(SprintRequest sprintRequest, Long marathonId, Marathon marathon){
        Sprint sprint = sprintRepository.getOne(marathonId);
        return fillSprintFromSprintRequest(sprintRequest, sprint, marathon);

}

    @Override
    public Sprint getSprintById(Long id) {
        Optional<Sprint> sprint = sprintRepository.findById(id);
        return sprint.get();// new EntityNotFoundException("No marathon exist for given id");
    }
}
