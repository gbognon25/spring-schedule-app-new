package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.config.PasswordEncoder;
import com.sparta.springscheduleappnew.dto.UserRequestDto;
import com.sparta.springscheduleappnew.dto.UserResponseDto;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.errors.CustomException;
import com.sparta.springscheduleappnew.errors.ErrorCode;
import com.sparta.springscheduleappnew.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserResponseDto createUser(UserRequestDto userDto) {

        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new CustomException(ErrorCode.EXISTING_USERNAME);
        }

        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new CustomException(ErrorCode.EXISTING_EMAIL);
        }

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

        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getUsername().equals(userDto.getUsername()) &&
                userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new CustomException(ErrorCode.EXISTING_USERNAME);
        }

        if (!user.getEmail().equals(userDto.getEmail()) &&
                userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new CustomException(ErrorCode.EXISTING_EMAIL);
        }

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return new UserResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}

