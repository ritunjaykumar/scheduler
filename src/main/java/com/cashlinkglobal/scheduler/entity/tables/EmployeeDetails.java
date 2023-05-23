package com.cashlinkglobal.scheduler.entity.tables;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity //employee_detail_new
@Table(name = "EMPLOYEE_DETAIL_NEW")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDetails implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(generator = "employee_credential_details_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "employee_credential_details_id", sequenceName = "employee_credential_id_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "EMP_ID", unique = true, nullable = false, updatable = false)
    private String employeeId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "DOB", nullable = false)
    private String dob;

    @Column(name = "doj", nullable = false, columnDefinition = "DATE", updatable = false)
    private LocalDate doj;

    @Column(name = "EMAIL_ID", nullable = false)
    private String emailId;

    @Column(name = "MOBILE_NUM", nullable = false)
    private String mobileNumber;

    @Column(name = "PROFILE_URL")
    private String profileUrl;
}
