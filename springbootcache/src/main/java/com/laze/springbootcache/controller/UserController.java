package com.laze.springbootcache.controller;

import com.laze.springbootcache.domain.entity.User;
import com.laze.springbootcache.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
