package com.sparta.springscheduleappnew.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message="사용자 이름을 입력하세요")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank(message = "Email을 입력하세요")
    @Email(message = "Email은 유효해야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 6, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;
}

