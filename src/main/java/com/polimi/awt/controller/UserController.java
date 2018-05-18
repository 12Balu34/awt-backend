package com.polimi.awt.controller;

import com.polimi.awt.model.User;
import com.polimi.awt.payload.SignupRequest;
import com.polimi.awt.repository.RoleRepository;
import com.polimi.awt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/users")
    private User createUser(@RequestBody SignupRequest signupRequest){

        User newUser = new User (signupRequest.getUsername(), signupRequest.getPassword(), signupRequest.getEmailAddress());

        newUser.addRole(roleRepository.findByName(signupRequest.roleToRoleName()));
        return userRepository.save(newUser);
    }
}