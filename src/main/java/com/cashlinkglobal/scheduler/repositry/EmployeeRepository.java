package com.cashlinkglobal.scheduler.repositry;

import com.cashlinkglobal.scheduler.entity.projections.WishEmployeeProjection;
import com.cashlinkglobal.scheduler.entity.tables.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDetails,Long> {


    /**
     *  >>flag
     *  1:new joining
     *  2:dob
     *  3:dob & anniversary
     *  4:anniversary
     * @return NotificationBannerProjection
     */
    @Query("SELECT new com.cashlinkglobal.scheduler.entity.projections.WishEmployeeProjection( " +
            "       concat(ed.firstName, ' ',ed.lastName ), " +
            "       ed.employeeId, " +
            "       ed.profileUrl, " +
            "       CASE " +
            "       WHEN ed.doj = current_date THEN 1 " +
            "         WHEN ( ( Extract(month FROM ed.doj) = Extract(month FROM current_date) " +
            "                  AND Extract(day FROM ed.doj) = Extract(day FROM current_date) ) " +
            "                AND ( Extract(month FROM ed.dob) = Extract(month FROM current_date) " +
            "                      AND Extract(day FROM ed.dob) = Extract(day FROM current_date) " +
            "                    ) ) " +
            "       THEN 3 " +
            "         WHEN ( Extract(month FROM ed.doj) = Extract(month FROM current_date) " +
            "                AND Extract(day FROM ed.doj) = Extract(day FROM current_date) ) " +
            "       THEN 4 " +
            "         WHEN ( Extract(month FROM ed.dob) = Extract(month FROM current_date) " +
            "                AND Extract(day FROM ed.dob) = Extract(day FROM current_date) ) " +
            "       THEN 2 " +
            "       END AS OPTION " +
            "      ) " +
            "FROM   EmployeeDetails ed " +
            "WHERE  ed.doj = current_date " +
            "        OR ( Extract(month FROM ed.dob) = Extract(month FROM current_date) " +
            "             AND Extract(day FROM ed.dob) = Extract(day FROM current_date) ) " +
            "        OR ( Extract(month FROM ed.doj) = Extract(month FROM current_date) " +
            "             AND Extract(day FROM ed.doj) = Extract(day FROM current_date) ) ")
    List<WishEmployeeProjection> getWishesEmployee();
}
