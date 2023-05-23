package com.cashlinkglobal.scheduler.entity.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@AllArgsConstructor
public class WishEmployeeProjection implements Serializable {
    private final String name;
    private final String empId;
    private final String image;
    private final int flag;

}
