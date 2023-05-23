package com.cashlinkglobal.scheduler.service.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceImplTest {
    @Autowired
    private EmployeeService employeeService;
    @Test
    void sendWishesNotification() {
        employeeService.sendWishesNotification();
    }
}