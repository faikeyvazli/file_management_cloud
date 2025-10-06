package com.project.filestorage.controller;

import com.project.filestorage.dto.AuthRequestDto;
import com.project.filestorage.model.Role;
import com.project.filestorage.model.User;
import com.project.filestorage.repository.RoleRepository;
import com.project.filestorage.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody AuthRequestDto authRequestDto){
        return userService.register(authRequestDto);
    }
    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDto authRequestDto){
        return userService.verify(authRequestDto);
    }
}
