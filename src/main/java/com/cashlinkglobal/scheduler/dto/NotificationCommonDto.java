package com.cashlinkglobal.scheduler.dto;

import com.cashlinkglobal.scheduler.entity.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class NotificationCommonDto {
    private String title;
    private String body;
    private String image;
    private Map<String, Object> data;
    private LocalDateTime start;
    private LocalDateTime end;
    private NotificationType notificationType;
}
