package com.example.quartz.dto;

import lombok.Data;

@Data
public class JobRequest {

    private String jobName;
    private String triggerName;
    private String group;
    private String cron;

    public JobRequest(String jobName, String triggerName, String group, String cron) {
        this.jobName = jobName;
        this.triggerName = triggerName;
        this.group = group;
        this.cron = cron;
    }
}
