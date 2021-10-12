package com.example.quartz.service;

import com.example.quartz.dto.JobInfo;
import com.example.quartz.dto.JobRequest;
import com.example.quartz.dto.RescheduleRequest;
import com.example.quartz.dto.SimpleData;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        jobDataMap.put("data", new SimpleData("aa", "bb"));
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

    public List<JobInfo> getSchedule() throws SchedulerException {
        List<JobInfo> list = new ArrayList<>();
        for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.anyGroup())) {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            scheduler.getTriggersOfJob(jobKey).stream().forEach(trigger -> {
                JobInfo info = new JobInfo();
                
                // jobDataMap에 저장한 데이터 조회
                info.setData((SimpleData) jobDetail.getJobDataMap().get("data"));

                info.setGroup(jobKey.getGroup());
                info.setJobName(jobKey.getName());
                info.setTriggerName(trigger.getKey().getName());

                if (trigger instanceof CronTrigger)
                    info.setCron(((CronTrigger) trigger).getCronExpression());

                list.add(info);
            });
        }

        return list;
    }
}
