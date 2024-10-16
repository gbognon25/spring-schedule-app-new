package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.config.PasswordEncoder;
import com.sparta.springscheduleappnew.dto.UserRequestDto;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = new PasswordEncoder();
    }

    public User createUser(UserRequestDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 비밀번호 암호화
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserRequestDto userDto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDto.getUsername());
                    user.setEmail(userDto.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User가 존재하지 않습니다."));
    }

    public User login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        throw new RuntimeException("User명이나 비밀번호가 일치하지 않습니다.");
    }


    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

