package com.example.exp4.controller;

import com.example.exp4.dto.UserResponse;
import com.example.exp4.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<UserResponse> list() {
        return userService.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.isEnabled()))
                .toList();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
