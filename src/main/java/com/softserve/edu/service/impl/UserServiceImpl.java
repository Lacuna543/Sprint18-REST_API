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


@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final MarathonRepository marathonRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

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
        UserResponse userResponse = null;
        User user = userRepository.getUserByEmail(userRequest.getEmail());
        if (user != null) {
            userResponse = new UserResponse(user);
        }
        return userResponse;
    }   

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(("No user /w id "+id)));
    }

//    public User createOrUpdateUser(User entity) {
//        return userRepository.save(entity);

//    }

//    public UserResponse createOrUpdateUser(CreateUserRequest userRequest) {
//
//        User newUser = new User();
//        if (userRequest.getUserId() != null) {
//
//            Optional<User> userOptional = userRepository.findById(userRequest.getUserId());
//
//            if (userOptional.isPresent()) {
//                newUser = userOptional.get();
//            }
//        }
//
//        newUser.setEmail(userRequest.getEmail());
//        newUser.setPassword(userRequest.getPassword());
//        newUser.setFirstName(userRequest.getFirstName());
//        newUser.setLastName(userRequest.getLastName());
//        newUser.setRole(userRequest.getRole());
//
//        userRepository.save(newUser);
//        return new UserResponse(newUser);
//    }

    public UserResponse createUser(CreateOrUpdateUserRequest request) {
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(roleRepository.findByName(request.getRole()));//<--HARD CODE!!
        userRepository.save(newUser);
        return new UserResponse(newUser);
    }

    @Override
    public UserResponse updateUser(CreateOrUpdateUserRequest request, Long id) {
        User newUser =userRepository.getOne(id);
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(roleRepository.findByName(request.getRole()));
        userRepository.save(newUser);
        return new UserResponse(newUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean addUserToMarathon(User user, Marathon marathon) {
        User userEntity = userRepository.getOne(user.getId());
        Marathon marathonEntity = marathonRepository.getOne(marathon.getId());
        marathonEntity.getUsers().add(userEntity);
        return marathonRepository.save(marathonEntity) != null;
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
