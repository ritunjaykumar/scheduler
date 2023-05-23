package com.cashlinkglobal.scheduler.service.api;

import com.cashlinkglobal.scheduler.dto.ApiRespDto;
import com.cashlinkglobal.scheduler.dto.NotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class Api {
    private final Logger logger = LoggerFactory.getLogger(Api.class);
    private static final String AUTH_CODE = "authCode";
    @Value("${cgs.api.cashlink-service.base-url}")
    private String notificationBaseUrl;
    @Value("${cgs.api.cashlink-service.group-notification}")
    private String groupNotification;
    @Value("${cgs.api.cashlink-service.notification-auth-code}")
    private String notificationAuthCode;
    @Autowired
    private RestTemplate restTemplate;
    private HttpHeaders headers;

    @PostConstruct
    private void init() {
        headers = new HttpHeaders();
        headers.add(AUTH_CODE, notificationAuthCode);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    public void sendNotification(NotificationDto notificationDto) {
        String url = notificationBaseUrl + groupNotification;
        logger.info(">> single notification url: {}", url);
        try {
            HttpEntity<NotificationDto> request = new HttpEntity<>(notificationDto, headers);
            ResponseEntity<ApiRespDto> response
                    = restTemplate.exchange(url, HttpMethod.POST, request, ApiRespDto.class);
            ApiRespDto body = response.getBody();
            logger.info(">> got response");
            if (response.getStatusCode() == HttpStatus.OK && body != null) {
                if (body.getRespCode().equalsIgnoreCase("cgs000")) {
                    logger.info(" >> notification send successfully");
                }
                logger.info(">> response code: {}", response.getStatusCode());
            } else {
                logger.info(">> status code: {}", response.getStatusCode());
            }
        } catch (Exception ex) {
            logger.info(">> server internal error: and error: {}", ex.getMessage());
        }
    }


}
