package com.sparta.springscheduleappnew.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="사용자 이름을 입력하세요")
    @Size(min = 8, max = 30, message = "사용자 이름은 8~30자 사이여야 합니다.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Email을 입력하세요")
    @Email(message = "Email은 유효해야 합니다.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 8, max = 80, message = "비밀번호는 8~80자 사이여야 합니다.")
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSchedule> userSchedules = new ArrayList<>();
}

