package com.sparta.springscheduleappnew.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserScheduleResponseDto {
    private Long userId;
    private String username;
    private Long scheduleId;
    private String scheduleTitle;

    public UserScheduleResponseDto(Long userId, String username, Long scheduleId, String scheduleTitle) {
        this.userId = userId;
        this.username = username;
        this.scheduleId = scheduleId;
        this.scheduleTitle = scheduleTitle;
    }
}

