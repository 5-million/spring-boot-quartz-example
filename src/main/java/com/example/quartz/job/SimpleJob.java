package com.example.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class SimpleJob extends CustomJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("execute simple job !!!!");
    }
}
