package com.example.quartz.service;

import com.example.quartz.dto.JobRequest;
import com.example.quartz.dto.RescheduleRequest;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final Scheduler scheduler;

    public void register(Class<? extends Job> job, JobRequest request)
            throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(job, request.getJobName(), request.getGroup());
        Trigger trigger = buildTrigger(request.getTriggerName(), request.getGroup(), request.getCron());

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private JobDetail buildJobDetail(Class<? extends Job> job, String jobName, String group) {
        return JobBuilder.newJob(job)
                .withIdentity(jobName, group)
                .setJobData(buildJobDataMap(jobName, group))
                .build();
    }

    private JobDataMap buildJobDataMap(String jobName, String group) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", jobName);
        jobDataMap.put("group", group);
        return jobDataMap;
    }

    private Trigger buildTrigger(String triggerName, String group, String cron) {
        return TriggerBuilder.newTrigger()
                .withIdentity(triggerName, group)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }

    public void delete(String jobName, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, group);
        scheduler.deleteJob(jobKey);
    }

    public void reschedule(RescheduleRequest request) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(request.getTriggerName(), request.getGroup());
        Trigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(request.getCron()))
                .build();

        scheduler.rescheduleJob(triggerKey, newTrigger);
    }
}
