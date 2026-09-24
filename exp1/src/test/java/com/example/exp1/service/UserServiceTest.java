package com.example.exp1.service;

import com.example.exp1.entity.User;
import com.example.exp1.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// AI-assisted: 密码加密单测（无需数据库），人工已复核。
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @Spy
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    UserService userService;

    private User userWith(String password) {
        User user = new User();
        user.setUsername("test");
        user.setPassword(password);
        user.setEmail("test@example.com");
        return user;
    }

    @Test
    void createShouldHashRawPassword() {
        when(userMapper.findById(any())).thenReturn(userWith("hashed"));

        userService.create(userWith("123456"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).insert(captor.capture());
        String stored = captor.getValue().getPassword();
        assertTrue(stored.startsWith("$2a$") || stored.startsWith("$2b$"),
                "新建用户密码应为 BCrypt 哈希，实际为: " + stored);
        assertTrue(passwordEncoder.matches("123456", stored));
    }

    @Test
    void createShouldNotDoubleHashExistingHash() {
        String existing = "$2a$10$JvRGGTicRNoIc1q99CQNFuRXsZQJrHgLHzusRDL.CFUcanJqm1/cC";
        when(userMapper.findById(any())).thenReturn(userWith(existing));

        userService.create(userWith(existing));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).insert(captor.capture());
        assertEquals(existing, captor.getValue().getPassword());
    }

    @Test
    void updateWithBlankPasswordShouldKeepExistingHash() {
        String existing = "$2a$10$JvRGGTicRNoIc1q99CQNFuRXsZQJrHgLHzusRDL.CFUcanJqm1/cC";
        when(userMapper.findById(1L)).thenReturn(userWith(existing));

        userService.update(1L, userWith("  "));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).update(captor.capture());
        assertEquals(existing, captor.getValue().getPassword());
    }

    @Test
    void updateWithRawPasswordShouldHash() {
        String existing = "$2a$10$JvRGGTicRNoIc1q99CQNFuRXsZQJrHgLHzusRDL.CFUcanJqm1/cC";
        when(userMapper.findById(1L)).thenReturn(userWith(existing));

        userService.update(1L, userWith("newpass"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).update(captor.capture());
        String stored = captor.getValue().getPassword();
        assertTrue(passwordEncoder.matches("newpass", stored));
    }
}
