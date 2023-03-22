package com.cashlinkglobal.scheduler.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ATTENDANCE_DETAILS")
public class AttendanceDetails implements Serializable {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(
            generator = "attendance_detail_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "attendance_detail_id",
            sequenceName = "attendance_detail_id_sequence",
            allocationSize = 1
    )
    private long id;
    @Column(name = "EMPLOYEE_ID", nullable = false, updatable = false)
    private String employeeId;
    @Column(name = "ATTENDANCE_DATE", updatable = false, nullable = false)
    private LocalDate attendanceDate;
    @Column(name = "IN_TIME")
    private LocalTime inTime;
    @Column(name = "OUT_TIME")
    private LocalTime outTime;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LEAVE_TYPE", referencedColumnName = "ID")
    private LeaveTypeDetails leaveType;

}
