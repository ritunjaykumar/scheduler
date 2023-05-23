package com.cashlinkglobal.scheduler.entity.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE_SETTING")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSetting {
    @Id
    @Column(name = "ID")
    @GeneratedValue(
            generator = "employee_setting_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "employee_setting_id",
            sequenceName = "employee_setting_id_sequence",
            allocationSize = 1
    )
    private int id;

    @Column(name = "EMP_ID", nullable = false, length = 10)
    private Long empId;

    @Column(name = "MAX_LEAVE", nullable = false)
    private double maxLeave;

    @Column(name = "MONTHLY_LEAVE", nullable = false)
    private double monthlyLeave;

    @Column(name = "FCM_TOKEN", nullable = false)
    private String fcmToken;


}
