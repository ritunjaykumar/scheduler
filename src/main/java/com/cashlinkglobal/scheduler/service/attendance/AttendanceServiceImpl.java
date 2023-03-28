package com.cashlinkglobal.scheduler.service.attendance;

import com.cashlinkglobal.scheduler.entity.AttendanceDetails;
import com.cashlinkglobal.scheduler.entity.EmployeeCredential;
import com.cashlinkglobal.scheduler.entity.LeaveTypeDetails;
import com.cashlinkglobal.scheduler.repositry.AttendanceRepository;
import com.cashlinkglobal.scheduler.service.employee.EmployeeService;
import com.cashlinkglobal.scheduler.service.leave.LeaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Value("${cgs.default.leave_type_id}")
    private long defaultLeaveTypeId;

    @Override
    @Transactional
    public void saveAttendanceData() {
        LeaveTypeDetails leaveDetails = leaveService.findLeaveDetailsById(defaultLeaveTypeId);
        if (leaveDetails == null) {
            logger.info(">> today attendance has not made because of wrong leave type id: {}", defaultLeaveTypeId);
            return;
        }
        logger.info(">> default leave details: {}", leaveDetails);
        List<EmployeeCredential> employees = employeeService.getEmployees();
        for (EmployeeCredential employee : employees) {
            saveAttendanceRecord(employee, leaveDetails);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void saveAttendanceRecord(final EmployeeCredential employeeCredential, final LeaveTypeDetails leaveType) {

        AttendanceDetails attendanceDetails = AttendanceDetails.builder()
                .employeeId(employeeCredential.getEmployeeId())
                .attendanceDate(LocalDate.now())
                .description("Employee has taken leave on: " + LocalDate.now())
                .leaveType(leaveType)
                .build();
        logger.info(">> saving attendance details: {}", attendanceDetails);
        try {
            AttendanceDetails unused = attendanceRepository.save(attendanceDetails);
            attendanceRepository.flush();
            logger.info(">> attendance has saved for id: {}", employeeCredential.getEmployeeId());
        } catch (Exception ex) {
            logger.info(">> unable to save the attendance details: {}", ex.getMessage());
        }
    }
}
