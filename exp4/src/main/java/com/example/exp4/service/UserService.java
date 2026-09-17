package com.example.exp4.service;

import com.example.exp4.entity.User;
import com.example.exp4.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(String username, String password) {
        if (userMapper.findByUsername(username) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        userMapper.insert(user);

        Long roleId = userMapper.findRoleIdByCode("USER");
        userMapper.insertUserRole(user.getId(), roleId);
        return user;
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public boolean delete(Long id) {
        return userMapper.delete(id) > 0;
    }
}
