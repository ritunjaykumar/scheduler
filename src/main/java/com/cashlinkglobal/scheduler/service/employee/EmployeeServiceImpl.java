package com.cashlinkglobal.scheduler.service.employee;


import com.cashlinkglobal.scheduler.entity.EmployeeDetails;
import com.cashlinkglobal.scheduler.repositry.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDetails> getEmployees() {
        try {
            logger.info(">> getting all employee details");
            List<EmployeeDetails> all = employeeRepository.findAll();
            logger.info(">> got all employee details count: {}", all.size());
            return all;
        } catch (Exception ex) {
            logger.info(">> error: {}", ex.getMessage());
        }
        return new ArrayList<>();
    }
}
