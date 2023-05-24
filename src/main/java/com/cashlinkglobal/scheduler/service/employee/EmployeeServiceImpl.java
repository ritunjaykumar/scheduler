package com.cashlinkglobal.scheduler.service.employee;


import com.cashlinkglobal.scheduler.dto.NotificationCommonDto;
import com.cashlinkglobal.scheduler.dto.NotificationDto;
import com.cashlinkglobal.scheduler.entity.enums.NotificationType;
import com.cashlinkglobal.scheduler.entity.projections.WishEmployeeProjection;
import com.cashlinkglobal.scheduler.entity.tables.CelebrationTemplate;
import com.cashlinkglobal.scheduler.entity.tables.EmployeeDetails;
import com.cashlinkglobal.scheduler.repositry.CelebrationRepository;
import com.cashlinkglobal.scheduler.repositry.EmployeeRepository;
import com.cashlinkglobal.scheduler.repositry.EmployeeSettingRepository;
import com.cashlinkglobal.scheduler.service.api.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CelebrationRepository celebrationRepository;
    @Autowired
    private EmployeeSettingRepository employeeSettingRepository;
    @Autowired
    private Api api;

    @Override
    public List<EmployeeDetails> getEmployees() {
        try {
            logger.info(">> getting all employee details");
            List<EmployeeDetails> all = employeeRepository.findAll();
            logger.info(">> got all employee details count: {}", all.size());
            return all;
        } catch (Exception ex) {
            logger.info(">> error: {}", ex.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void sendWishesNotification() {
        logger.info(">> sending wishes Notifications");
        List<WishEmployeeProjection> wishEmployeeProjections;
        try {
            wishEmployeeProjections = employeeRepository.getWishesEmployee();
            logger.info(">> all banner celebration: {}", wishEmployeeProjections.size());
        } catch (Exception ex) {
            logger.info(">> celebration-banner error: {}", ex.getMessage());
            return;
        }
        if (wishEmployeeProjections.isEmpty()) {
            logger.info(">> celebration data is empty");
        }

        List<CelebrationTemplate> celebrationTemplates;
        try {
            List<CelebrationTemplate> celebrationTemplateList = celebrationRepository
                    .getAllCelebration(
                            CelebrationRepository.birthdayCelebration,
                            CelebrationRepository.anniversaryCelebration,
                            CelebrationRepository.newJoinCelebration
                    );

            logger.info(">> celebration template list: {}", celebrationTemplateList.size());
            celebrationTemplates = celebrationTemplateList;
        } catch (Exception ex) {
            logger.info(">> celebration-banner error: {}", ex.getMessage());
            return;
        }

        List<String> fcmTokens;
        try {
            fcmTokens = employeeSettingRepository.findAllFcmToken();
        } catch (Exception ex) {
            logger.info(">> getting fcm token error: {}", ex.getMessage());
            return;
        }



        /*>>flag
         * 1:new joining
         * 2:dob
         * 3:dob & anniversary
         * 4:anniversary
         */

        final List<NotificationCommonDto> notificationCommonDtos = new ArrayList<>();


        for (WishEmployeeProjection nbp : wishEmployeeProjections) {
            NotificationCommonDto respDto = null;
            if (nbp.getFlag() == 1) {
                respDto = getNewJoiningTemp(celebrationTemplates);
            } else if (nbp.getFlag() == 2) {
                respDto = getBirthdayTemp(celebrationTemplates);
            } else if (nbp.getFlag() == 4) {
                respDto = getAnniversaryTemp(celebrationTemplates);
            } else if (nbp.getFlag() == 3) {
                respDto = getAnniversaryTemp(celebrationTemplates);
                //for birthday
                NotificationCommonDto birthdayTemp = getBirthdayTemp(celebrationTemplates);
                birthdayTemp.setStart(LocalDateTime.now());
                LocalDateTime now = LocalDateTime.now().plusDays(1);
                LocalTime timeNow = LocalTime.parse("22:00:00");
                LocalDateTime endTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                        timeNow.getHour(), timeNow.getMinute());
                birthdayTemp.setEnd(endTime);

                final Map<String, Object> data = new HashMap<>();
                data.put("empId", nbp.getEmpId());
                birthdayTemp.setData(data);

                notificationCommonDtos.add(birthdayTemp);


            }
            if (respDto != null) {
                respDto.setStart(LocalDateTime.now());
                LocalDateTime now = LocalDateTime.now().plusDays(1);
                LocalTime timeNow = LocalTime.parse("22:00:00");
                LocalDateTime endTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                        timeNow.getHour(), timeNow.getMinute());
                respDto.setEnd(endTime);

                final Map<String, Object> data = new HashMap<>();
                data.put("empId", nbp.getEmpId());
                respDto.setData(data);
                notificationCommonDtos.add(respDto);

            }

        }

        NotificationDto notificationDto = NotificationDto.builder()
                .fcms(fcmTokens)
                .payload(notificationCommonDtos)
                .build();

        api.sendNotification(notificationDto);

        System.out.println("test final notificationCommonDtos : " + notificationCommonDtos);

    }


    private NotificationCommonDto getBirthdayTemp(List<CelebrationTemplate> celebrationTemplates) {
        CelebrationTemplate celebrationTemplate = getCelebrationTemplate(celebrationTemplates,
                CelebrationRepository.birthdayCelebration);
//        System.out.println("Birthday temp: "+celebrationTemplate);
        if (celebrationTemplate != null) {
            return NotificationCommonDto.builder()
                    .title(celebrationTemplate.getTitle())
                    .body(celebrationTemplate.getBody())
                    .image(celebrationTemplate.getImage())
                    .notificationType(NotificationType.BIRTHDAY)
                    .build();
        } else {
            return NotificationCommonDto.builder()
                    .title("Birthday")
                    .body("Best wishes for a joyous day filled with love and laughter. " +
                            "May your days be filled with sunshine, beautiful colours and wishes to come.")
                    .notificationType(NotificationType.BIRTHDAY)
                    .build();
        }

    }

    private NotificationCommonDto getAnniversaryTemp(List<CelebrationTemplate> celebrationTemplates) {
        CelebrationTemplate celebrationTemplate = getCelebrationTemplate(celebrationTemplates,
                CelebrationRepository.anniversaryCelebration);
//        System.out.println("anniversary temp: "+celebrationTemplate);
        if (celebrationTemplate != null) {
            return NotificationCommonDto.builder()
                    .title(celebrationTemplate.getTitle())
                    .body(celebrationTemplate.getBody())
                    .image(celebrationTemplate.getImage())
                    .notificationType(NotificationType.ANNIVERSARY)
                    .build();
        } else {
            return NotificationCommonDto.builder()
                    .title("Year completion")
                    .body("Best wishes for a joyous day filled with love and laughter. May your days be filled " +
                            "with sunshine, beautiful colours and wishes to come.")
                    .notificationType(NotificationType.ANNIVERSARY)
                    .build();
        }

    }

    private NotificationCommonDto getNewJoiningTemp(List<CelebrationTemplate> celebrationTemplates) {
        CelebrationTemplate celebrationTemplate = getCelebrationTemplate(celebrationTemplates,
                CelebrationRepository.newJoinCelebration);
        if (celebrationTemplate != null) {
            return NotificationCommonDto.builder()
                    .title(celebrationTemplate.getTitle())
                    .body(celebrationTemplate.getBody())
                    .image(celebrationTemplate.getImage())
                    .notificationType(NotificationType.NEW_JOINING)
                    .build();
        } else {
            return NotificationCommonDto.builder()
                    .title("New Joining")
                    .body("Best wishes for a joyous day filled with love and laughter. May your days be " +
                            "filled with sunshine, beautiful colours and wishes to come.")
                    .notificationType(NotificationType.NEW_JOINING)
                    .build();
        }

    }

    private CelebrationTemplate getCelebrationTemplate(List<CelebrationTemplate> celebrationTemplates, String celType) {
        for (CelebrationTemplate ct : celebrationTemplates) {
            if (Objects.equals(ct.getCelebrationType(), celType)) {
                return ct;
            }
        }
        return null;
    }

}
