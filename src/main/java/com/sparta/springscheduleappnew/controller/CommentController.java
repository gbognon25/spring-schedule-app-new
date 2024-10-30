package com.sparta.springscheduleappnew.controller;

import com.sparta.springscheduleappnew.dto.CommentRequestDto;
import com.sparta.springscheduleappnew.dto.CommentResponseDto;
import com.sparta.springscheduleappnew.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody @Valid CommentRequestDto commentDto, @RequestParam Long userId, @RequestParam Long scheduleId) {
        CommentResponseDto responseDto = commentService.createComment(commentDto, userId, scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        CommentResponseDto responseDto = commentService.getCommentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getCommentsBySchedule(@RequestParam Long scheduleId) {
        List<CommentResponseDto> comments = commentService.getAllCommentsForSchedule(scheduleId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id, @RequestBody @Valid CommentRequestDto commentDto) {
        CommentResponseDto responseDto = commentService.updateComment(id, commentDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}

