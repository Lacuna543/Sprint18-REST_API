package com.softserve.edu.controller;

import com.softserve.edu.config.JwtProvider;
import com.softserve.edu.dto.MarathonRequest;
import com.softserve.edu.dto.MarathonResponse;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Role;
import com.softserve.edu.model.RoleData;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import javassist.NotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@Slf4j
public class MarathonController {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    private MarathonService marathonService;
    private MarathonRepository marathonRepository;
    private UserService studentService;
    private JwtProvider jwtProvider;

    public MarathonController(MarathonService marathonService,
                              MarathonRepository marathonRepository,
                              UserService studentService,
                              JwtProvider jwtProvider) {
        this.marathonService = marathonService;
        this.studentService = studentService;
        this.jwtProvider = jwtProvider;
        this.marathonRepository = marathonRepository;
    }
    
    @GetMapping("/marathons")
    public List<Marathon> listMarathons(@RequestHeader("Authorization") String token) {
        log.info("**/marathons");
        String login = jwtProvider.getLoginFromToken(token.substring(BEARER.length()));
        User user = studentService.findByLogin(login);
        Role userRole = user.getRole();
        if (userRole.getName().equals(RoleData.ADMIN.toString())) {
            return marathonService.getAll();
        } else {
            return marathonRepository.getAllByUsers(user);
        }
    }

    @PostMapping("/create-marathon")
    public MarathonResponse createMarathon(MarathonRequest marathonRequest) {
        log.info("**/create-marathon");
        Marathon marathon = marathonService.createMarathon(marathonRequest);
        return new MarathonResponse(marathon);
    }

    @PutMapping("/marathons/edit/{id}")
    public MarathonResponse updateMarathon(@PathVariable long id, MarathonRequest marathonRequest) throws NotFoundException {
        log.info("**/marathons/edit/" + id);
        Marathon marathon = marathonService.update(marathonRequest, id);
        return new MarathonResponse(marathon);
    }

    @DeleteMapping("/marathons/delete/{id}")
    public String deleteMarathon(@PathVariable long id) {
        log.info("**/marathons/delete/" + id);
       marathonService.deleteMarathonById(id);
        return "successfully deleted marathon with id " + id;
    }

}
