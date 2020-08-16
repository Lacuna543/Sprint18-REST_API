package com.softserve.edu.service.impl;

import com.softserve.edu.dto.CreateOrUpdateUserRequest;
import com.softserve.edu.dto.UserRequest;
import com.softserve.edu.dto.UserResponse;
import com.softserve.edu.exception.EntityNotFoundException;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.repository.RoleRepository;
import com.softserve.edu.repository.UserRepository;
import com.softserve.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final MarathonRepository marathonRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           MarathonRepository marathonRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.marathonRepository = marathonRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByLogin(String login) {
        return userRepository.getUserByEmail(login);
    }

    public UserResponse findByLoginAndPassword(UserRequest userRequest) {
        User user = userRepository.getUserByEmail(userRequest.getEmail());
        return (user != null && passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) ? new UserResponse(user) : null;

    }

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream().map(UserResponse::new).collect(Collectors.toList());
        return userResponses;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(("No user /w id " + id)));
    }

    @Override
    public UserResponse createUser(CreateOrUpdateUserRequest request) {
        User newUser = new User();
        return new UserResponse(fillUserFromUserRequest(request, newUser));
    }

    private User fillUserFromUserRequest(CreateOrUpdateUserRequest request, User newUser) {
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(roleRepository.findByName(request.getRole()));
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public UserResponse updateUser(CreateOrUpdateUserRequest request, Long id) {
        User newUser = userRepository.getOne(id);
        return new UserResponse(fillUserFromUserRequest(request, newUser));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Marathon addUserToMarathon(User user, Marathon marathon) {
        marathon.getUsers().add(user);
        return marathonRepository.save(marathon);
    }

    @Override
    public boolean deleteUserFromMarathon(User user, Marathon marathon) {
        User userEntity = userRepository.getOne(user.getId());
        Marathon marathonEntity = marathonRepository.getOne(marathon.getId());
        marathonEntity.getUsers().remove(userEntity);
        return marathonRepository.save(marathonEntity) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not Found!");
        }
        return user;
    }
}
