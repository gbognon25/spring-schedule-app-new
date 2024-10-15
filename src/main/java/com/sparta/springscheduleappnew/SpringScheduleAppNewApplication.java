package com.sparta.springscheduleappnew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringScheduleAppNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringScheduleAppNewApplication.class, args);
    }

}
