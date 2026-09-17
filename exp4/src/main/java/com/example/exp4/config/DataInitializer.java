package com.example.exp4.config;

import com.example.exp4.entity.User;
import com.example.exp4.mapper.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createIfAbsent("admin", "admin123", "ADMIN");
        createIfAbsent("user", "user123", "USER");
    }

    private void createIfAbsent(String username, String rawPassword, String roleCode) {
        if (userMapper.findByUsername(username) != null) {
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEnabled(true);
        userMapper.insert(user);
        userMapper.insertUserRole(user.getId(), userMapper.findRoleIdByCode(roleCode));
    }
}
