package com.cashlinkglobal.scheduler.service.calender;

import com.cashlinkglobal.scheduler.entity.CalenderDetails;

import java.time.LocalDate;

public interface CalenderService {
    CalenderDetails findCalenderDetailsByDate(LocalDate today);
}
