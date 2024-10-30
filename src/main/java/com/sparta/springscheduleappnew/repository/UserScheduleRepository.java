package com.sparta.springscheduleappnew.repository;

import com.sparta.springscheduleappnew.entity.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {

    List<UserSchedule> findByScheduleId(Long scheduleId);

    void deleteByUserIdAndScheduleId(Long userId, Long scheduleId);

    boolean existsByUserIdAndScheduleId(Long userId, Long scheduleId);
}

