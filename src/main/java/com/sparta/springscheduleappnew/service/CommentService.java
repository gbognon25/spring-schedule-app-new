package com.sparta.springscheduleappnew.service;

import com.sparta.springscheduleappnew.dto.CommentRequestDto;
import com.sparta.springscheduleappnew.entity.Comment;
import com.sparta.springscheduleappnew.entity.Schedule;
import com.sparta.springscheduleappnew.entity.User;
import com.sparta.springscheduleappnew.repository.CommentRepository;
import com.sparta.springscheduleappnew.repository.ScheduleRepository;
import com.sparta.springscheduleappnew.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

//    public Comment createComment(@Valid CommentRequestDto comment, Long userId, Long scheduleId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("user가 존재하지 않습니다."));
//        Schedule schedule = scheduleRepository.findById(scheduleId)
//                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
//        comment.setAuthor(user);
//        comment.setSchedule(schedule);
//        return commentRepository.save(comment);
//    }

    public Comment createComment(CommentRequestDto commentDto, Long userId, Long scheduleId) {
        // Retrieve the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User가 존재하지 않습니다."));

        // Retrieve the schedule by scheduleId
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));

        // Create and save the comment
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setAuthor(user); // Set the user
        comment.setSchedule(schedule); // Set the schedule
        return commentRepository.save(comment);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getAllCommentsForSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(Schedule::getComments)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
    }

    public Comment updateComment(Long id, @Valid CommentRequestDto updatedComment) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setContent(updatedComment.getContent());
                    return commentRepository.save(comment);
                }).orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
