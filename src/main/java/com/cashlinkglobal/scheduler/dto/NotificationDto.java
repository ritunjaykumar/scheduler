package com.cashlinkglobal.scheduler.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
public class NotificationDto implements Serializable {
    List<NotificationCommonDto> payload;
    private List<String> fcms;
}
