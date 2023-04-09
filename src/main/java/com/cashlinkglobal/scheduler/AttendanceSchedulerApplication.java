package com.cashlinkglobal.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
public class AttendanceSchedulerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AttendanceSchedulerApplication.class, args);
    }

}
