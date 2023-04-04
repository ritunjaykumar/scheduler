package com.cashlinkglobal.scheduler.entity;


import com.cashlinkglobal.scheduler.enums.AttendanceStatus;
import com.cashlinkglobal.scheduler.enums.AttendanceStatusConverter;
import lombok.*;

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
@Builder
@ToString
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

    @Column(name = "PRESENT_FLAG", nullable = false)
    @Convert(converter = AttendanceStatusConverter.class)
    private AttendanceStatus flag;

    @Column(name = "DAY", nullable = false, length = 20)
    private String day;

    @Column(name = "ATTENDANCE_ENABLED", nullable = false)
    private boolean attendanceEnabled;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LEAVE_TYPE", referencedColumnName = "ID")
    private LeaveTypeDetails leaveType;

}
