package com.cashlinkglobal.scheduler.entity.tables;

import com.cashlinkglobal.scheduler.entity.enums.StatusFlag;
import com.cashlinkglobal.scheduler.entity.enums.StatusFlagConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "CELEBRATION_TEMPLATE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CelebrationTemplate implements Serializable {
    @Id
    @GeneratedValue(
            generator = "celebration_template_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "celebration_template_id",
            sequenceName = "common_id_sequence",
            allocationSize = 1
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    @Column(name = "BODY", nullable = false, length = 250)
    private String body;

    @Column(name = "DESCRIPTION", nullable = false, length = 150)
    private String description;

    @Column(name = "STATUS_FLAG", nullable = false, length = 150)
    @Convert(converter = StatusFlagConverter.class)
    private StatusFlag statusFlag;

    @Column(name = "CELEBRATION_TYPE", nullable = false, length = 150)
    private String celebrationType;

    @Column(name = "IMAGE", nullable = false, length = 150)
    private String image;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;


}
