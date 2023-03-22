package com.cashlinkglobal.scheduler.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE_CREDENTIAL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeCredential implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(generator = "employee_credential_details_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "employee_credential_details_id", sequenceName = "employee_credential_id_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "EMPLOYEE_ID", unique = true, nullable = false, updatable = false)
    private String employeeId;
    @Column(name = "USER_NAME", nullable = false, unique = true, length = 100, updatable = false)
    private String userName;
    @Column(name = "MOBILE_NUMBER", nullable = false, length = 10, unique = true, updatable = false)
    private String mobileNumber;
    @Column(name = "EMAIL_ID", nullable = false, length = 100, unique = true, updatable = false)
    private String emailId;

    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;


}
