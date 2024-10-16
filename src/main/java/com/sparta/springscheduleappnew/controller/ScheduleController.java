package com.sparta.springscheduleappnew.controller;

import com.sparta.springscheduleappnew.dto.ScheduleRequestDto;
import com.sparta.springscheduleappnew.dto.ScheduleResponseDto;
import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PagedResourcesAssembler<ScheduleResponseDto> pagedResourcesAssembler;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, PagedResourcesAssembler<ScheduleResponseDto> pagedResourcesAssembler) {
        this.scheduleService = scheduleService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid ScheduleRequestDto scheduleDto, @RequestParam Long authorId) {
        Schedule createdSchedule = scheduleService.createSchedule(scheduleDto, authorId);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(createdSchedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id)
                .map(schedule -> {
                    ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
                    return ResponseEntity.ok(responseDto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PagedModel<ScheduleResponseDto>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ScheduleResponseDto> schedulePage = scheduleService.getSchedules(page, size);

        //Page를 PagedModel로 변환
        PagedModel<ScheduleResponseDto> pagedModel = pagedResourcesAssembler.toModel(schedulePage);

        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id, @RequestBody @Valid ScheduleRequestDto scheduleDto) {
        Schedule updatedSchedule = scheduleService.updateSchedule(id, scheduleDto);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(updatedSchedule);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}

