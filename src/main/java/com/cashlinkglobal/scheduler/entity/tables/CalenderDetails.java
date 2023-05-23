package com.cashlinkglobal.scheduler.entity.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "CALENDER_DETAILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalenderDetails implements Serializable {
    @Id
    @Column(name = "ID", unique = true)
    @GeneratedValue(
            generator = "calender_details_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "calender_details_id",
            sequenceName = "calender_details_id_sequence",
            allocationSize = 1
    )
    private int id;
    @Column(name = "FESTIVAL_NAME", nullable = false, unique = true)
    private String festivalName;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "FEST_DATE", columnDefinition = "DATE", nullable = false, unique = true)
    private LocalDate date;
    @Column(name = "STATUS", nullable = false, unique = true, length = 1)
    private int status;
    @Column(name = "STATE", nullable = false, length = 50)
    private String state;
}
