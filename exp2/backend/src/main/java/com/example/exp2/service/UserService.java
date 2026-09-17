package com.example.exp2.service;

import com.example.exp2.entity.User;
import com.example.exp2.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public User create(User user) {
        userMapper.insert(user);
        return findById(user.getId());
    }

    public User update(Long id, User user) {
        user.setId(id);
        userMapper.update(user);
        return findById(id);
    }

    public boolean delete(Long id) {
        return userMapper.delete(id) > 0;
    }
}
