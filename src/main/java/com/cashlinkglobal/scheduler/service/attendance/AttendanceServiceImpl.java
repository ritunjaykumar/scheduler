package com.cashlinkglobal.scheduler.service.attendance;

import com.cashlinkglobal.scheduler.entity.AttendanceDetails;
import com.cashlinkglobal.scheduler.repositry.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final Logger logger = LoggerFactory.getLogger(AttendanceServiceImpl.class);
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Override
    @Transactional
    public void saveAttendanceData() {

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void saveAttendanceRecord(AttendanceDetails attendanceDetails){

    }
}
