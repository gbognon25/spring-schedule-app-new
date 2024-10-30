package com.sparta.springscheduleappnew.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {

    @NotBlank(message = "제목을 입력하세요")
    private String title;

    private String description;
}

