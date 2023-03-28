package com.cashlinkglobal.scheduler.service.leave;

import com.cashlinkglobal.scheduler.entity.LeaveTypeDetails;

public interface LeaveService {
    LeaveTypeDetails findLeaveDetailsById(long id);
}
