package com.cashlinkglobal.scheduler.scheduler;

import com.cashlinkglobal.scheduler.service.attendance.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SchedulerStarter {
    private final Logger logger = LoggerFactory.getLogger(SchedulerStarter.class);

    @Autowired
    private SchedulerService schedulerService;

    @Scheduled(cron = "${cgs.scheduler.attendance.interval-in-cron}")
    public void runForAttendanceInitializer() {
        logger.info(">> scheduler has started at: {}", LocalDateTime.now());
        schedulerService.saveAttendanceData();
    }

    @Scheduled(cron = "${cgs.scheduler.leave.interval-in-cron}")
    public void runToIncreaseLeaveBalance(){
        logger.info(">> scheduler has started for runToIncreaseLeaveBalance at: {}", LocalDateTime.now());
        schedulerService.increaseAttendance();
    }


}
