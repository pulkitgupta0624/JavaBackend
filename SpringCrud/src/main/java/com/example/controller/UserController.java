package com.example.controller;

import com.example.service.UserService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser(String name) {
        userService.addUser(name);
        System.out.println("User created: " + name);
    }

    public void listUsers() {
        List<String> users = userService.getAllUsers();
        System.out.println("Users list: " + users);
    }
}
