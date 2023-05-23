package com.cashlinkglobal.scheduler.entity.enums;

import lombok.Getter;

@Getter
public enum AttendanceStatus {
    PRESENT(0), LEAVE(-1), WFH(1);

    private final int flag;

    AttendanceStatus(int flag) {
        this.flag = flag;
    }

    public static AttendanceStatus of(int flag) {
        for (AttendanceStatus as : AttendanceStatus.values()) {
            if (as.flag == flag) {
                return as;
            }
        }
        return AttendanceStatus.LEAVE;
    }
}
