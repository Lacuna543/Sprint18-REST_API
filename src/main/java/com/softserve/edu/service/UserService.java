package com.softserve.edu.service;

import com.softserve.edu.dto.CreateUserRequest;
import com.softserve.edu.dto.UserRequest;
import com.softserve.edu.dto.UserResponse;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getUserById(Long id);
    UserDetails loadUserByUsername(String username);
    boolean createUser(CreateUserRequest userRequest);
    boolean createOrUpdateUser(UserRequest userRequest);
    void deleteUserById(Long id);
    boolean addUserToMarathon(User user, Marathon marathon);
    boolean deleteUserFromMarathon(User user, Marathon marathon);

    public User findByLogin(String login);
    UserResponse findByLoginAndPassword(UserRequest userRequest);

}
