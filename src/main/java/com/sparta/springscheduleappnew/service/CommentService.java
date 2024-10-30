package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.dto.CommentRequestDto;
import com.sparta.springscheduleappnew.dto.CommentResponseDto;
import com.sparta.springscheduleappnew.entity.Comment;
import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.repository.CommentRepository;
import com.sparta.springscheduleappnew.repository.ScheduleRepository;
import com.sparta.springscheduleappnew.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto createComment(CommentRequestDto commentDto, Long userId, Long scheduleId) {
        // userId로 user를 검색
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User가 존재하지 않습니다."));

        //scheduleId로 일정을 검색
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));

        // 대글의 생성 및 저장
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .author(user) // Set the user
                .schedule(schedule) // Set the schedule
                .build();

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public Optional<CommentResponseDto> getCommentById(Long id) {
        return commentRepository
                .findById(id)
                .map(CommentResponseDto::new);
    }

    public List<CommentResponseDto> getAllCommentsForSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(schedule -> schedule.getComments().stream()
                        .map(CommentResponseDto::new)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다"));

        Comment updatedComment = Comment.builder()
                .id(comment.getId())
                .content(commentDto.getContent())
                .build();

        // Save the updated comment
        commentRepository.save(updatedComment);

        return new CommentResponseDto(updatedComment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}

