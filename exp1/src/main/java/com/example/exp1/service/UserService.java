package com.example.exp1.service;

import com.example.exp1.entity.User;
import com.example.exp1.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// AI-assisted: 密码 BCrypt 加密逻辑（新建必加密；更新时空密码保留原值、已是哈希则不重复加密），人工已复核。
@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public User create(User user) {
        user.setPassword(encodeIfRaw(user.getPassword()));
        userMapper.insert(user);
        return findById(user.getId());
    }

    public User update(Long id, User user) {
        User existing = findById(id);
        String password = user.getPassword();
        if (password == null || password.isBlank()) {
            // 更新时未填密码：保留数据库中的原哈希，避免被空值覆盖
            user.setPassword(existing != null ? existing.getPassword() : null);
        } else {
            user.setPassword(encodeIfRaw(password));
        }
        user.setId(id);
        userMapper.update(user);
        return findById(id);
    }

    public boolean delete(Long id) {
        return userMapper.delete(id) > 0;
    }

    private String encodeIfRaw(String password) {
        if (password != null && !isBcryptHash(password)) {
            return passwordEncoder.encode(password);
        }
        return password;
    }

    static boolean isBcryptHash(String value) {
        return value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$");
    }
}
