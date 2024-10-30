package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.dto.ScheduleRequestDto;
import com.sparta.springscheduleappnew.dto.ScheduleResponseDto;
import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.repository.ScheduleRepository;
import com.sparta.springscheduleappnew.repository.UserRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleDto, Long authorId) {
        // authorId로 사용자(작성자)를 검색
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("작성자가 존재하지 않습니다."));

        // 일정의 생성과 저장
        Schedule schedule = Schedule.builder()
                .title(scheduleDto.getTitle())
                .description(scheduleDto.getDescription())
                .author(author)
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    public Optional<ScheduleResponseDto> getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .map(ScheduleResponseDto::new);
    }

    public Page<ScheduleResponseDto> getSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleRepository.findAllByOrderByUpdatedAtDesc(pageable)
                .map(ScheduleResponseDto::new);
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleDto) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));

        Schedule updatedSchedule = Schedule.builder()
                .id(existingSchedule.getId())
                .title(scheduleDto.getTitle())
                .description(scheduleDto.getDescription())
                .comments(existingSchedule.getComments())
                .userSchedules(existingSchedule.getUserSchedules())
                .author(existingSchedule.getAuthor())
                .build();

        Schedule savedSchedule = scheduleRepository.save(updatedSchedule);
        return new ScheduleResponseDto(savedSchedule);
    }


    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}

