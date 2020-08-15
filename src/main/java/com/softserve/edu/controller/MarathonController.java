package com.softserve.edu.controller;

import com.softserve.edu.config.JwtProvider;
import com.softserve.edu.dto.MarathonRequest;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Role;
import com.softserve.edu.model.RoleData;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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
    public String createMarathon(MarathonRequest marathonRequest) {
        log.info("**/create-marathon");
        Marathon marathon = new Marathon();
        marathon.setTitle(marathonRequest.getTitle());
        marathonService.createOrUpdate(marathon);
        return "marathon successfully created";
    }

    @GetMapping("/marathons/edit/{id}")
    public String updateMarathon(@PathVariable long id, MarathonRequest marathonRequest) {
        log.info("**/marathons/edit/" + id);
        Marathon marathon = marathonService.getMarathonById(id);
        marathon.setTitle(marathonRequest.getTitle());
        marathonService.createOrUpdate(marathon);
        return "marathon successfully modified";
    }

    @GetMapping("/marathons/delete/{id}")
    public String deleteMarathon(@PathVariable long id) {
        log.info("**/marathons/delete/" + id);
        marathonService.deleteMarathonById(id);
        return "marathon successfully deleted";
    }







//old methods


//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/students/{marathon_id}")
//    public String getStudentsFromMarathon(@PathVariable("marathon_id") long marathonId, Model model) {
//        WebAuthenticationToken authentication
//                = (WebAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("TRAINEE"))) {
//            return "redirect:/sprints/" + marathonId;
//        }
//        List<User> students = studentService.getAll().stream().filter(
//                student -> student.getMarathons().stream().anyMatch(
//                        marathon -> marathon.getId() == marathonId)).collect(Collectors.toList());
//        Marathon marathon = marathonService.getMarathonById(marathonId);
//        model.addAttribute("students", students);
//        model.addAttribute("all_students", studentService.getAll());
//        model.addAttribute("marathon", marathon);
//        return "marathon-students";
//    }




}
