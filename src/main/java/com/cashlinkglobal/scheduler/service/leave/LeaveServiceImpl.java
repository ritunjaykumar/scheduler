package com.cashlinkglobal.scheduler.service.leave;

import com.cashlinkglobal.scheduler.entity.tables.LeaveTypeDetails;
import com.cashlinkglobal.scheduler.repositry.LeaveTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeaveServiceImpl implements LeaveService {
    private final Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveTypeDetails findLeaveDetailsById(long id) {
        try {
            Optional<LeaveTypeDetails> leaveTypeDetailsOtp = leaveTypeRepository.findById(id);
            if (leaveTypeDetailsOtp.isEmpty()) {
                logger.info(">> leave details is not find for id: {}", id);
                return null;
            }
            logger.info(">> record find for id: {}", id);
            return leaveTypeDetailsOtp.get();
        } catch (Exception ex) {
            logger.info(">> error occurred: {}", ex.getMessage());
            return null;
        }
    }
}
