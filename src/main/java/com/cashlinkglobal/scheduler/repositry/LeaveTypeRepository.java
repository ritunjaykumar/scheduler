package com.cashlinkglobal.scheduler.repositry;

import com.cashlinkglobal.scheduler.entity.tables.LeaveTypeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveTypeDetails, Long> {
}
