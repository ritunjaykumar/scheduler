package com.cashlinkglobal.scheduler.repositry;

import com.cashlinkglobal.scheduler.entity.EmployeeCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeCredential,Long> {
}
