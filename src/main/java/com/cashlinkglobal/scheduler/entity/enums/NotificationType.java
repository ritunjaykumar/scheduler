package com.cashlinkglobal.scheduler.entity.enums;

import lombok.Getter;

@Getter
public enum NotificationType {
    BIRTHDAY(1), NEW_JOINING(2), ANNIVERSARY(3), OTHER(4), LEAVE(5);
    private final int notificationType;

    NotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public static NotificationType of(int type) {
        for (NotificationType nt : NotificationType.values()) {
            if (nt.notificationType == type) {
                return nt;
            }
        }
        return NotificationType.OTHER;
    }
}
