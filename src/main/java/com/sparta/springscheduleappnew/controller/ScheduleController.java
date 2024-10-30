package com.sparta.springscheduleappnew.controller;

import com.sparta.springscheduleappnew.dto.ScheduleRequestDto;
import com.sparta.springscheduleappnew.dto.ScheduleResponseDto;
import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid ScheduleRequestDto scheduleDto, @RequestParam Long authorId) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(scheduleDto, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.getScheduleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ScheduleResponseDto>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ScheduleResponseDto> schedulePage = scheduleService.getSchedules(page, size);

        //Page<ScheduleResponseDto>를 PagedModel<ScheduleResponseDto>로 변환
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                schedulePage.getSize(),
                schedulePage.getNumber(),
                schedulePage.getTotalElements(),
                schedulePage.getTotalPages()
        );

        PagedModel<ScheduleResponseDto> pagedModel = PagedModel.of(schedulePage.getContent(), pageMetadata);

        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id, @RequestBody @Valid ScheduleRequestDto scheduleDto) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, scheduleDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}

