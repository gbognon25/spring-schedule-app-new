package com.sparta.springscheduleappnew.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserScheduleResponseDto {
    private Long userId;
    private String username;
    private String scheduleTitle;

    public UserScheduleResponseDto(Long userId, String username, String scheduleTitle) {
        this.userId = userId;
        this.username = username;
        this.scheduleTitle = scheduleTitle;
    }
}

