package com.cashlinkglobal.scheduler.service.calender;

import com.cashlinkglobal.scheduler.entity.CalenderDetails;
import com.cashlinkglobal.scheduler.repositry.CalenderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CalenderServiceImpl implements CalenderService {
    private final Logger logger = LoggerFactory.getLogger(CalenderServiceImpl.class);
    @Autowired
    private CalenderRepository calenderRepository;

    @Override
    public CalenderDetails findCalenderDetailsByDate(LocalDate today) {
        logger.info(">> finding calender details by date: {}", today);
        try {
            Optional<CalenderDetails> calDetOpt = calenderRepository.findByDate(today);
            if (calDetOpt.isPresent()) {
                logger.info(">> today is {}", calDetOpt.get().getFestivalName());
                return calDetOpt.get();
            }
            logger.info(">> There is no festival today!");
        } catch (Exception ex) {
            logger.info(">> exception occurred error: {}", ex.getMessage());
        }
        return null;
    }
}
