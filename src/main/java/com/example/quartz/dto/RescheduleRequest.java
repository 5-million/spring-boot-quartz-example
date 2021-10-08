package com.example.quartz.dto;

import lombok.Data;

@Data
public class RescheduleRequest {

    private String triggerName;
    private String group;
    private String cron;
}
