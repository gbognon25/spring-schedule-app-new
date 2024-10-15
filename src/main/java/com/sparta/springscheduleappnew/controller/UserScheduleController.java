package com.sparta.springscheduleappnew.controller;

import com.sparta.springscheduleappnew.dto.UserScheduleResponseDto;
import com.sparta.springscheduleappnew.entity.UserSchedule;
import com.sparta.springscheduleappnew.service.UserScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-schedules")
public class UserScheduleController {

    private final UserScheduleService userScheduleService;

    @Autowired
    public UserScheduleController(UserScheduleService userScheduleService) {
        this.userScheduleService = userScheduleService;
    }

    @PostMapping
    public ResponseEntity<UserScheduleResponseDto> assignUserToSchedule(
            @RequestParam Long userId, @RequestParam Long scheduleId) {
        UserSchedule userSchedule = userScheduleService.assignUserToSchedule(userId, scheduleId);
        UserScheduleResponseDto responseDto = new UserScheduleResponseDto(
                userSchedule.getUser().getId(),
                userSchedule.getUser().getUsername(),
                userSchedule.getSchedule().getId(),
                userSchedule.getSchedule().getTitle()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeUserFromSchedule(@RequestParam Long userId, @RequestParam Long scheduleId) {
        userScheduleService.removeUserFromSchedule(userId, scheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<UserScheduleResponseDto>> getUsersAssignedToSchedule(@PathVariable Long scheduleId) {
        List<UserScheduleResponseDto> responseDtos = userScheduleService.getUsersAssignedToSchedule(scheduleId)
                .stream()
                .map(userSchedule -> new UserScheduleResponseDto(
                        userSchedule.getUser().getId(),
                        userSchedule.getUser().getUsername(),
                        userSchedule.getSchedule().getId(),
                        userSchedule.getSchedule().getTitle()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }
}

