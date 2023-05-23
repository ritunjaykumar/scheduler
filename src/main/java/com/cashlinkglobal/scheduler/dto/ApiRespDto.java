package com.cashlinkglobal.scheduler.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiRespDto {
    private  String respCode;
    private  String respStatus;
    private  Object data;
    private  String message;
}
