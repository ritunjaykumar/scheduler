package com.cashlinkglobal.scheduler.service.attendance;

import com.cashlinkglobal.scheduler.entity.AttendanceDetails;
import com.cashlinkglobal.scheduler.entity.CalenderDetails;
import com.cashlinkglobal.scheduler.entity.EmployeeCredential;
import com.cashlinkglobal.scheduler.entity.LeaveTypeDetails;
import com.cashlinkglobal.scheduler.enums.AttendanceStatus;
import com.cashlinkglobal.scheduler.repositry.AttendanceRepository;
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
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private CalenderService calenderService;
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


        List<EmployeeCredential> employees = employeeService.getEmployees();
        for (EmployeeCredential employee : employees) {

            AttendanceDetails attendanceDetails = AttendanceDetails.builder()
                    .employeeId(employee.getEmployeeId())
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
