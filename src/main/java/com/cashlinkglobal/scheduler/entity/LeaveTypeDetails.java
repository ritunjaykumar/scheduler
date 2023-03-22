package com.cashlinkglobal.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "LEAVE_TYPE_DETAILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveTypeDetails {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(
            generator = "leave_type_details_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "leave_type_details_id",
            sequenceName = "leave_type_details_id_sequence",
            allocationSize = 1
    )
    private long id;
    @Column(name = "NAME", nullable = false, length = 100, updatable = false, unique = true)
    private String name;
    @Column(name = "SHORT_NAME", nullable = false, length = 15, updatable = false, unique = true)
    private String shortName;
    @Column(name = "DESCRIPTION", nullable = false, length = 300)
    private String description;
}
