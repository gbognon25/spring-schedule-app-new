package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.repository.ScheduleRepository;
import com.sparta.springscheduleappnew.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Schedule createSchedule(Schedule schedule, Long authorId) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        schedule.setAuthor(author);
        return scheduleRepository.save(schedule);
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setTitle(updatedSchedule.getTitle());
                    schedule.setDescription(updatedSchedule.getDescription());
                    return scheduleRepository.save(schedule);
                }).orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}

