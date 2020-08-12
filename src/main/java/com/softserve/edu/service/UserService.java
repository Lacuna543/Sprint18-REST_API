package com.softserve.edu.service;

import com.softserve.edu.dto.CreateOrUpdateUserRequest;
import com.softserve.edu.dto.UserRequest;
import com.softserve.edu.dto.UserResponse;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getUserById(Long id);
    UserDetails loadUserByUsername(String username);
    UserResponse createUser(CreateOrUpdateUserRequest userRequest);
    UserResponse updateUser(CreateOrUpdateUserRequest userRequest, Long id);
//    boolean createOrUpdateUser(UserRequest userRequest);
    void deleteUserById(Long id);
    boolean addUserToMarathon(User user, Marathon marathon);
    boolean deleteUserFromMarathon(User user, Marathon marathon);

    public User findByLogin(String login);
    UserResponse findByLoginAndPassword(UserRequest userRequest);

}
