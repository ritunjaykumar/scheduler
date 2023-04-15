package com.cashlinkglobal.scheduler.service.attendance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SchedulerServiceImplTest {
    @Autowired
    private  SchedulerService schedulerService;
    @Test
    void increaseAttendance() {
        schedulerService.increaseAttendance();
    }
}