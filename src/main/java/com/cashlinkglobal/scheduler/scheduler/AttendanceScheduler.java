package com.cashlinkglobal.scheduler.scheduler;

import com.cashlinkglobal.scheduler.service.attendance.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AttendanceScheduler {
    private final Logger logger = LoggerFactory.getLogger(AttendanceScheduler.class);

    @Autowired
    private AttendanceService attendanceService;

    @Scheduled(cron = "${cgs.attendance.interval-in-cron}")
    public void runForAttendanceInitializer() {
        logger.info(">> scheduler has started at: {}", LocalDateTime.now().toLocalDate());
        attendanceService.saveAttendanceData();
    }


}
