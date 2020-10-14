package com.example.messagingservice.controller;

import com.example.messagingservice.enumeration.ResponseStatusType;
import com.example.messagingservice.request.BlockUserRequest;
import com.example.messagingservice.request.LoginUserRequest;
import com.example.messagingservice.request.RegisterUserRequest;
import com.example.messagingservice.response.Response;
import com.example.messagingservice.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public Response register(@Valid @RequestBody RegisterUserRequest request) {
        userService.registerUser(request);
        return new Response(ResponseStatusType.SUCCESS.getValue());
    }

    @PostMapping(path = "/login")
    public Response login(@Valid @RequestBody LoginUserRequest request) {
        userService.loginUser(request);
        return new Response(ResponseStatusType.SUCCESS.getValue());
    }

    @PostMapping(path = "/block")
    public Response blockUser(@Valid @RequestBody BlockUserRequest request) {
        userService.blockUser(request);
        return new Response(ResponseStatusType.SUCCESS.getValue());
    }
}
