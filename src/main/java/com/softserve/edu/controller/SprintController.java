package com.softserve.edu.controller;

import com.softserve.edu.dto.CreateOrUpdateUserRequest;
import com.softserve.edu.dto.SprintRequest;
import com.softserve.edu.dto.SprintResponse;
import com.softserve.edu.dto.UserResponse;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Sprint;
import com.softserve.edu.model.User;
//import com.softserve.edu.security.WebAuthenticationToken;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.SprintService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Data
@Slf4j
public class SprintController {

    private SprintService sprintService;
    private MarathonService marathonService;

    public SprintController(SprintService sprintService, MarathonService marathonService) {
        this.sprintService = sprintService;
        this.marathonService = marathonService;
    }

    @PostMapping("/create-sprint/{marathon_id}")
    public SprintResponse createSprint(@PathVariable("marathon_id") Long marathonId, SprintRequest sprintRequest) {
        Sprint sprint = sprintService.addSprintToMarathon(sprintRequest, marathonService.getMarathonById(marathonId));
        return new SprintResponse(sprint);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update-sprint/{marathon_id}")
    public SprintResponse updateSprint(SprintRequest sprintRequest, @PathVariable("marathon_id") Long marathonId, Marathon marathon) {
        log.info("**/update-sprint");
        marathon = marathonService.getMarathonById(marathonId);
        Sprint sprint = sprintService.updateSprint(sprintRequest, marathonId, marathon);

        return new SprintResponse(sprint);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TRAINEE') and @sprintController.getMarathonIdByUser(#marathonId)")
    @GetMapping("/sprints/{marathon_id}")
    public String getAllSprintsFromMarathon(@PathVariable("marathon_id") long marathonId, Model model) {
        List<Sprint> sprints = sprintService.getSprintsByMarathonId(marathonId);
        model.addAttribute("sprints", sprints);
        model.addAttribute("marathon_id", marathonId);
        return "sprints";
    }

//    public boolean getMarathonIdByUser(long marathonId) {
//        WebAuthenticationToken authentication
//                = (WebAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getDetails();
//        return user.getMarathons().stream().anyMatch(marathon -> marathon.getId() == marathonId);
//    }
}
