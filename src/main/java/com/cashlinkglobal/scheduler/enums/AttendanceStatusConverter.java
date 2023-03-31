package com.cashlinkglobal.scheduler.enums;

import com.cashlinkglobal.scheduler.entity.AttendanceDetails;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AttendanceStatusConverter implements AttributeConverter<AttendanceStatus, Integer> {


    @Override
    public Integer convertToDatabaseColumn(AttendanceStatus attendanceStatus) {
        return attendanceStatus.getFlag();
    }

    @Override
    public AttendanceStatus convertToEntityAttribute(Integer flag) {
        return AttendanceStatus.of(flag);
    }
}
