package com.example.quartz.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobInfo {

    private String jobName;
    private String triggerName;
    private String group;
    private String cron;
}
