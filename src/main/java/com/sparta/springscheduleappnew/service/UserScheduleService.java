package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.dto.UserScheduleResponseDto;
import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.entity.UserSchedule;
import com.sparta.springscheduleappnew.repository.ScheduleRepository;
import com.sparta.springscheduleappnew.repository.UserRepository;
import com.sparta.springscheduleappnew.repository.UserScheduleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserScheduleService {

    private final UserScheduleRepository userScheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public UserScheduleResponseDto assignUserToSchedule(Long userId, Long scheduleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User가 존재하지 않습니다."));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));

        UserSchedule userSchedule = UserSchedule.builder()
                .user(user)
                .schedule(schedule)
                .build();

        UserSchedule savedUserSchedule = userScheduleRepository.save(userSchedule);
        return new UserScheduleResponseDto(
                savedUserSchedule.getUser().getId(),
                savedUserSchedule.getUser().getUsername(),
                savedUserSchedule.getSchedule().getTitle()
        );
    }

    @Transactional
    public void removeUserFromSchedule(Long userId, Long scheduleId) {
        userScheduleRepository.deleteByUserIdAndScheduleId(userId, scheduleId);
    }

    public List<UserScheduleResponseDto> getUsersAssignedToScheduleDtos(Long scheduleId) {
        return userScheduleRepository.findByScheduleId(scheduleId)
                .stream()
                .map(userSchedule -> new UserScheduleResponseDto(
                        userSchedule.getUser().getId(),
                        userSchedule.getUser().getUsername(),
                        userSchedule.getSchedule().getTitle()
                ))
                .collect(Collectors.toList());
    }
}

