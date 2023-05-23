package com.cashlinkglobal.scheduler.service.attendance;

import com.cashlinkglobal.scheduler.entity.tables.AttendanceDetails;
import com.cashlinkglobal.scheduler.entity.tables.CalenderDetails;
import com.cashlinkglobal.scheduler.entity.tables.EmployeeDetails;
import com.cashlinkglobal.scheduler.entity.tables.LeaveTypeDetails;
import com.cashlinkglobal.scheduler.entity.enums.AttendanceStatus;
import com.cashlinkglobal.scheduler.repositry.AttendanceRepository;
import com.cashlinkglobal.scheduler.repositry.EmployeeSettingRepository;
import com.cashlinkglobal.scheduler.service.calender.CalenderService;
import com.cashlinkglobal.scheduler.service.employee.EmployeeService;
import com.cashlinkglobal.scheduler.service.leave.LeaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {
    private final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private CalenderService calenderService;
    @Autowired
    private EmployeeSettingRepository employeeSettingRepository;
    @Value("${cgs.default.leave_type_id}")
    private long defaultLeaveTypeId;
    @Value("${cgs.default.public_leave_id}")
    private long publicLeaveId;


    @Override
    @Transactional
    public void saveAttendanceData() {

        LocalDate now = LocalDate.now();
        String description = "";
        long leaveType;
        boolean isWeekDay = isWeekDay(now);
        String dayName = now.getDayOfWeek().name().toLowerCase();
        boolean attendanceStatus;

        CalenderDetails calenderDetails = calenderService.findCalenderDetailsByDate(now);
        if (calenderDetails != null || isWeekDay) {
            //today is some festival, so it would be holiday for all
            if (calenderDetails != null && isWeekDay) {
                //festival as well as week day
                description = calenderDetails.getFestivalName()
                        + " : " + calenderDetails.getDescription()
                        + " : " + (now.getDayOfWeek().name().toLowerCase());
            } else if (isWeekDay) {
                description = "Today is " + dayName;
            } else {
                description = calenderDetails.getFestivalName()
                        + " : " + calenderDetails.getDescription();
            }
            attendanceStatus = false;
            leaveType = publicLeaveId;
        } else {
            //working day
            description = "Employee has taken leave on: " + LocalDate.now();
            leaveType = defaultLeaveTypeId;
            attendanceStatus = true;
        }


        LeaveTypeDetails leaveDetails = leaveService.findLeaveDetailsById(leaveType);
        if (leaveDetails == null) {
            logger.info(">> today attendance has not made because of wrong leave type id: {}", defaultLeaveTypeId);
            return;
        }
        logger.info(">> default leave details: {}", leaveDetails);


        List<EmployeeDetails> employees = employeeService.getEmployees();
        for (EmployeeDetails employee : employees) {

            AttendanceDetails attendanceDetails = AttendanceDetails.builder()
                    .employeeId(employee.getId())
                    .attendanceDate(now)
                    .description(description)
                    .leaveType(leaveDetails)
                    .flag(AttendanceStatus.LEAVE)
                    .day(dayName)
                    .attendanceEnabled(attendanceStatus)
                    .build();

            saveAttendanceRecord(attendanceDetails);
        }
    }

    @Override
    @Transactional
    public void increaseAttendance() {
        List<EmployeeDetails> employees = employeeService.getEmployees();
        int total = 0;
        List<String> missed = new ArrayList<>();
        for (EmployeeDetails ed : employees) {
            logger.info(">> increasing employee leave for id: {}", ed.getEmployeeId());
            try {
                int i = employeeSettingRepository.updateMaxLeaveByEmpId(ed.getEmployeeId());
                if (i == 1) {
                    total++;
                    System.out.println("info: " + i);
                    logger.info(">> employee leave has increased: {}", ed.getEmployeeId());
                } else {
                    missed.add(ed.getEmployeeId());
                }
            } catch (Exception ex) {
                logger.info(">> error: {}", ex.getMessage());
                missed.add(ed.getEmployeeId());
            }
        }
        logger.info(">> summery: ");
        System.out.println("total updated: " + total);
        System.out.println("list of user which missed: " + missed);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private synchronized void saveAttendanceRecord(final AttendanceDetails attendanceDetails) {

        logger.info(">> saving attendance details: {}", attendanceDetails);
        try {
            AttendanceDetails unused = attendanceRepository.save(attendanceDetails);
            attendanceRepository.flush();
            logger.info(">> attendance has saved for id: {}", attendanceDetails.getEmployeeId());
        } catch (Exception ex) {
            logger.info(">> unable to save the attendance details: {}", ex.getMessage());
        }
    }


    private boolean isWeekDay(LocalDate now) {
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int value = dayOfWeek.getValue();
        //7: sunday 6: saturday
        return value == 7 || value == 6;
    }
}
