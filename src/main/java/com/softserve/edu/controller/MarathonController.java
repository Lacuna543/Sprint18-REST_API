package com.softserve.edu.controller;

import com.softserve.edu.config.JwtProvider;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Role;
import com.softserve.edu.model.RoleData;
import com.softserve.edu.model.User;
//import com.softserve.edu.security.WebAuthenticationToken;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Data
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
  //new methods

    @GetMapping("/marathons")
    public List<Marathon> listMarathons(@RequestHeader("Authorization") String token) {
//        log.info("**/marathons");
        String login = jwtProvider.getLoginFromToken(token.substring(BEARER.length()));
        User user = studentService.findByLogin(login);
        Role userRole = user.getRole();
        if (userRole.getName().equals(RoleData.ADMIN.toString())) {
            return marathonService.getAll();
        } else {
            return marathonRepository.getAllByUsers(user);
        }
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/marathons/delete/{id}")
    public String deleteMarathon(@PathVariable long id) {
        marathonService.deleteMarathonById(id);
        return "redirect:/marathons";
    }







//old methods

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/create-marathon")
    public String createMarathon(Model model) {
        model.addAttribute("marathon", new Marathon());
        return "create-marathon";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @PostMapping("/marathons")
    public String createMarathon(@Validated @ModelAttribute Marathon marathon, BindingResult result) {
        if (result.hasErrors()) {
            return "create-marathon";
        }
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/marathons/edit/{id}")
    public String updateMarathon(@PathVariable long id, Model model) {
        Marathon marathon = marathonService.getMarathonById(id);
        model.addAttribute("marathon", marathon);
        return "update-marathon";
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @PostMapping("/marathons/edit/{id}")
    public String updateMarathon(@PathVariable long id, @ModelAttribute Marathon marathon, BindingResult result) {
        if (result.hasErrors()) {
            return "update-marathon";
        }
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }

//    @PreAuthorize("hasAuthority('MENTOR')")
//    @GetMapping("/marathons/delete/{id}")
//    public String deleteMarathon(@PathVariable long id) {
//        marathonService.deleteMarathonById(id);
//        return "redirect:/marathons";
//    }

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

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/marathons")
//    public String getAllMarathons(Model model) {
//        List<Marathon> marathons;
//        WebAuthenticationToken authentication
//                = (WebAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("MENTOR"))) {
//            marathons = marathonService.getAll();
//        } else {
//            User user = (User)authentication.getDetails();
//            marathons = user.getMarathons();
//        }
//        model.addAttribute("marathons", marathons);
//        return "marathons";
//    }


}
