package com.sparta.springscheduleappnew.controller;

import com.sparta.springscheduleappnew.dto.UserScheduleResponseDto;
import com.sparta.springscheduleappnew.entity.UserSchedule;
import com.sparta.springscheduleappnew.service.UserScheduleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-schedules")
@RequiredArgsConstructor
public class UserScheduleController {

    private final UserScheduleService userScheduleService;

    @PostMapping
    public ResponseEntity<UserScheduleResponseDto> assignUserToSchedule(
            @RequestParam Long userId, @RequestParam Long scheduleId) {
        UserScheduleResponseDto responseDto = userScheduleService.assignUserToSchedule(userId, scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeUserFromSchedule(@RequestParam Long userId, @RequestParam Long scheduleId) {
        userScheduleService.removeUserFromSchedule(userId, scheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<UserScheduleResponseDto>> getUsersAssignedToSchedule(@PathVariable Long scheduleId) {
        List<UserScheduleResponseDto> responseDtos = userScheduleService.getUsersAssignedToScheduleDtos(scheduleId);
        return ResponseEntity.ok(responseDtos);
    }
}
