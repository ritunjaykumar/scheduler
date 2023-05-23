package com.cashlinkglobal.scheduler.entity.enums;

import lombok.Getter;

@Getter
public enum StatusFlag {
    ACTIVE(0), IN_ACTIVE(1);
    private final int status;

    StatusFlag(int status) {
        this.status = status;
    }

    public static StatusFlag of(int i) {
        if (i == 0) {
            return StatusFlag.ACTIVE;
        } else {
            return StatusFlag.IN_ACTIVE;
        }
    }
}
