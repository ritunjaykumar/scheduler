package com.cashlinkglobal.scheduler.repositry;

import com.cashlinkglobal.scheduler.entity.tables.EmployeeSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeSettingRepository extends JpaRepository<EmployeeSetting,  Long> {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying
    @Query("update EmployeeSetting e set e.maxLeave = (e.maxLeave + e.monthlyLeave) where e.empId = ?1")
    int updateMaxLeaveByEmpId(String empId);

    @Query("select e.fcmToken from EmployeeSetting e where e.fcmToken is not null")
    List<String> findAllFcmToken();




}
