package com.example.exp2.controller;

import com.example.exp2.entity.User;
import com.example.exp2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "用户管理", description = "用户 CRUD 接口")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "查询全部用户")
    @GetMapping
    public List<User> list() {
        return userService.findAll();
    }

    @Operation(summary = "按 ID 查询用户")
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = userService.findById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "新增用户")
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @Operation(summary = "修改用户")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
