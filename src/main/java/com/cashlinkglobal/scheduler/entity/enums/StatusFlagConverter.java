package com.cashlinkglobal.scheduler.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusFlagConverter implements AttributeConverter<StatusFlag, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StatusFlag attribute) {
        return attribute.getStatus();
    }

    @Override
    public StatusFlag convertToEntityAttribute(Integer dbData) {
        return StatusFlag.of(dbData);
    }
}
