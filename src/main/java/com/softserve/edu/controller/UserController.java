package com.softserve.edu.controller;

import com.softserve.edu.config.JwtProvider;
import com.softserve.edu.dto.*;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Role;
import com.softserve.edu.model.User;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.RoleService;
import com.softserve.edu.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
@Data
@Slf4j
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private MarathonService marathonService;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;

    public UserController(UserService userService,
                          RoleService roleService,
                          MarathonService marathonService,
                          JwtProvider jwtProvider) {
        this.userService = userService;
        this.roleService = roleService;
        this.marathonService = marathonService;
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    @Qualifier("bCrypt")
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public OperationResponse signUp() {
        return new OperationResponse("true");
    }

    @PostMapping("/signup")
    public OperationResponse signUp(
            @RequestParam(value = "login", required = true)
                    String login,
            @RequestParam(value = "password", required = true)
                    String password) {
//        log.info("**/signup userLogin = " + login);
        CreateOrUpdateUserRequest userRequest = new CreateOrUpdateUserRequest(login, password);
        return new OperationResponse(String.valueOf(userService.createUser(userRequest)));
    }


    @PostMapping("/signin")
    public TokenResponse signIn(
            @RequestParam(value = "login", required = true)
                    String login,
            @RequestParam(value = "password", required = true)
                    String password) {
//        log.info("**/signin userLogin = " + login);
        UserRequest userRequest = new UserRequest(login, password);
        UserResponse userResponse = userService.findByLoginAndPassword(userRequest);
        return new TokenResponse(jwtProvider.generateToken(userResponse.getEmail()));
    }

    //updated
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-user")
    public UserResponse createUser(CreateOrUpdateUserRequest request) {
        log.info("**/create-user");
        return userService.createUser(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update-user/{user_id}")
    public UserResponse updateUser(CreateOrUpdateUserRequest request, @PathVariable ("user_id") Long userId) {
        log.info("**/update-user");
        return userService.updateUser(request, userId);
    }


    //updated
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("students/{marathon_id}/add")
    public String addUserToMarathon(@RequestParam("user_id") long userId, @PathVariable("marathon_id") long marathonId) {
        userService.addUserToMarathon(
                userService.getUserById(userId),
                marathonService.getMarathonById(marathonId));
        return "User added to marathon " + marathonId;
    }

//    @PreAuthorize("hasAuthority('MENTOR')")
//    @GetMapping("/students/{marathon_id}/edit/{student_id}")
//    public String updateStudent(@PathVariable("marathon_id") long marathonId, @PathVariable("student_id") long studentId,
//                                Model model) {
//        User user = userService.getUserById(studentId);
//        List<Role> roles = roleService.getAll();
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roles);
//        model.addAttribute("marathon_id", marathonId);
//        return "update-student";
//    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/students/{marathon_id}/delete/{student_id}")
    public String deleteUser(@PathVariable("marathon_id") long marathonId, @PathVariable("student_id") long studentId) {
        userService.deleteUserFromMarathon(
                userService.getUserById(studentId),
                marathonService.getMarathonById(marathonId));
        return "redirect:/students/" + marathonId;
    }

    //updated
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }


    //update
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable long id) {
        User user = userService.getUserById(id);
        for (Marathon marathon : user.getMarathons()) {
            userService.deleteUserFromMarathon(user, marathon);
        }
        userService.deleteUserById(id);
        return "Successfully deleted user with id " + id;
    }

    //updated
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{id}")
    public UserResponse showUser(@PathVariable("id") long id) {
        return new UserResponse(userService.getUserById(id));
    }

}
