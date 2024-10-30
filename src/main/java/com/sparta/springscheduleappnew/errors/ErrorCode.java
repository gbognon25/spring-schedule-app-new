package com.sparta.springscheduleappnew.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 errors
    NOT_NULL(HttpStatus.BAD_REQUEST, "필수 값 누락", 400),

    // 401 errors
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다.", 401),

    // 404 errors
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User가 존재하지 않습니다.", 404),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다.", 404),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다.", 404),

    // 409 errors
    EXISTING_USERNAME(HttpStatus.CONFLICT, "이미 사용되는 Username", 409),
    EXISTING_EMAIL(HttpStatus.CONFLICT, "이미 사용되는 Email", 409);

    private final HttpStatus status;
    private final String message;
    private final int code;
}
