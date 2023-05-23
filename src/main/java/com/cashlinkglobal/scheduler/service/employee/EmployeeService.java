package com.cashlinkglobal.scheduler.service.employee;

import com.cashlinkglobal.scheduler.entity.tables.EmployeeDetails;

import java.util.List;

public interface EmployeeService{
    List<EmployeeDetails> getEmployees();

    void sendWishesNotification();

}
