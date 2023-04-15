package com.cashlinkglobal.scheduler.repositry;

import com.cashlinkglobal.scheduler.entity.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDetails,Long> {
}
