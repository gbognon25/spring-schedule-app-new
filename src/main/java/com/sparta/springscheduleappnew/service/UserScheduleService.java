package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.entity.UserSchedule;
import com.sparta.springscheduleappnew.repository.ScheduleRepository;
import com.sparta.springscheduleappnew.repository.UserRepository;
import com.sparta.springscheduleappnew.repository.UserScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserScheduleService {

    private final UserScheduleRepository userScheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public UserScheduleService(UserScheduleRepository userScheduleRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.userScheduleRepository = userScheduleRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public UserSchedule assignUserToSchedule(Long userId, Long scheduleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        UserSchedule userSchedule = new UserSchedule();
        userSchedule.setUser(user);
        userSchedule.setSchedule(schedule);
        return userScheduleRepository.save(userSchedule);
    }

    @Transactional
    public void removeUserFromSchedule(Long userId, Long scheduleId) {
        userScheduleRepository.deleteByUserIdAndScheduleId(userId, scheduleId);
    }

    public List<UserSchedule> getUsersAssignedToSchedule(Long scheduleId) {
        return userScheduleRepository.findByScheduleId(scheduleId);
    }
}

