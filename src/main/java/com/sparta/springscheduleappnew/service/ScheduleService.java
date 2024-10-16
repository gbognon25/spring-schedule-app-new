package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.dto.ScheduleRequestDto;
import com.sparta.springscheduleappnew.dto.ScheduleResponseDto;
import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.repository.ScheduleRepository;
import com.sparta.springscheduleappnew.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public Schedule createSchedule(ScheduleRequestDto scheduleDto, Long authorId) {
        // authorId로 사용자(작성자)를 검색
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("작성자가 존재하지 않습니다."));

        // 일정의 생성과 저장
        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleDto.getTitle());
        schedule.setDescription(scheduleDto.getDescription());
        schedule.setAuthor(author);
        return scheduleRepository.save(schedule);
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public Page<ScheduleResponseDto> getSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleRepository.findAllByOrderByUpdatedAtDesc(pageable)
                .map(schedule -> new ScheduleResponseDto(schedule));
    }

    public Schedule updateSchedule(Long id, @Valid ScheduleRequestDto updatedSchedule) {
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setTitle(updatedSchedule.getTitle());
                    schedule.setDescription(updatedSchedule.getDescription());
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}

