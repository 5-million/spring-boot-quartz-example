package com.example.quartz.controller;

import com.example.quartz.dto.JobRequest;
import com.example.quartz.dto.RescheduleRequest;
import com.example.quartz.job.JobWithBean;
import com.example.quartz.job.SimpleJob;
import com.example.quartz.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService service;

    @PostMapping("/api/v1/schedule/simple")
    @ResponseStatus(HttpStatus.CREATED)
    public void createForSimpleJob(@RequestBody JobRequest request) throws SchedulerException {
        service.register(SimpleJob.class, request);
    }

    @PostMapping("/api/v1/schedule/bean")
    @ResponseStatus(HttpStatus.CREATED)
    public void createForJobWithBean(@RequestBody JobRequest request) throws SchedulerException {
        service.register(JobWithBean.class, request);
    }

    @PatchMapping("/api/v1/schedule")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody RescheduleRequest request) throws SchedulerException {
        service.reschedule(request);
    }

    @DeleteMapping("/api/v1/schedule")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@RequestParam("jobName") String jobName, @RequestParam("group") String group)
            throws SchedulerException {
        service.delete(jobName, group);
    }
}
