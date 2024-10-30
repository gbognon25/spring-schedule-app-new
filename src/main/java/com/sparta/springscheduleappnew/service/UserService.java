package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.config.PasswordEncoder;
import com.sparta.springscheduleappnew.dto.UserRequestDto;
import com.sparta.springscheduleappnew.dto.UserResponseDto;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserResponseDto createUser(UserRequestDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword())) // 비밀번호 암호화
                .build();
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    public UserResponseDto login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return new UserResponseDto(user);
        }
        throw new RuntimeException("User명이나 비밀번호가 일치하지 않습니다.");
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User가 존재하지 않습니다."));

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    public Optional<UserResponseDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDto::new);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

