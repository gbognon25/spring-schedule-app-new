package com.sparta.springscheduleappnew.controller;

import com.sparta.springscheduleappnew.dto.CommentRequestDto;
import com.sparta.springscheduleappnew.dto.CommentResponseDto;
import com.sparta.springscheduleappnew.entity.Comment;
import com.sparta.springscheduleappnew.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody @Valid CommentRequestDto commentDto, @RequestParam Long userId, @RequestParam Long scheduleId) {
        Comment createdComment = commentService.createComment(commentDto, userId, scheduleId);
        CommentResponseDto responseDto = new CommentResponseDto(createdComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .map(comment -> new ResponseEntity<>(new CommentResponseDto(comment), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsBySchedule(@PathVariable Long scheduleId) {
        List<CommentResponseDto> comments = commentService.getAllCommentsForSchedule(scheduleId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id, @RequestBody @Valid CommentRequestDto commentDto) {
        Comment updatedComment = commentService.updateComment(id, commentDto);
        CommentResponseDto responseDto = new CommentResponseDto(updatedComment);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}

