package com.cashlinkglobal.scheduler.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NotificationTypeConverter implements AttributeConverter<NotificationType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(NotificationType attribute) {
        return attribute.getNotificationType();
    }

    @Override
    public NotificationType convertToEntityAttribute(Integer dbData) {
        return NotificationType.of(dbData);
    }
}
