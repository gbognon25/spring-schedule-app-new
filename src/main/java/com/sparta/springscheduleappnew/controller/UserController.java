package com.sparta.springscheduleappnew.controller;

import com.sparta.springscheduleappnew.dto.LoginRequestDto;
import com.sparta.springscheduleappnew.dto.UserRequestDto;
import com.sparta.springscheduleappnew.dto.UserResponseDto;
import com.sparta.springscheduleappnew.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRequestDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입을 성공적으로 하였습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest) {
        userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok("정상적으로 로그인하였습니다.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.getUserById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDto userDto) {
        UserResponseDto responseDto = userService.updateUser(id, userDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

