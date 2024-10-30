package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.dto.CommentRequestDto;
import com.sparta.springscheduleappnew.dto.CommentResponseDto;
import com.sparta.springscheduleappnew.entity.Comment;
import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.errors.CustomException;
import com.sparta.springscheduleappnew.errors.ErrorCode;
import com.sparta.springscheduleappnew.repository.CommentRepository;
import com.sparta.springscheduleappnew.repository.ScheduleRepository;
import com.sparta.springscheduleappnew.repository.UserRepository;
import java.util.List;
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
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    // scheduleId로 일정을 검색
    Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        // 대글의 생성 및 저장
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .author(user)
                .schedule(schedule)
                .build();

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getAllCommentsForSchedule(Long scheduleId) {
    return scheduleRepository
        .findById(scheduleId)
        .map(
            schedule ->
                schedule.getComments().stream()
                    .map(CommentResponseDto::new)
                    .collect(Collectors.toList()))
        .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentDto) {
    Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        comment.setContent(commentDto.getContent());

        Comment updatedComment = commentRepository.save(comment);

        commentRepository.save(updatedComment);
        return new CommentResponseDto(updatedComment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        commentRepository.deleteById(id);
    }
}

