package com.sparta.springscheduleappnew.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnauthorizedException extends RuntimeException {
    private final ErrorCode errorCode;
}
