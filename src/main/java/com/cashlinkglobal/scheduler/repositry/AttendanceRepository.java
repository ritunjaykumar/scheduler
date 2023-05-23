package com.cashlinkglobal.scheduler.repositry;

import com.cashlinkglobal.scheduler.entity.tables.AttendanceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceDetails, Long> {

}
